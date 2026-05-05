package lat.divisas.sdk;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lat.divisas.sdk.builder.QueryBuilder;
import lat.divisas.sdk.cache.MemoryCache;
import lat.divisas.sdk.exceptions.DivisasException;
import lat.divisas.sdk.models.ErrorResponse;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

public class DivisasClient {

    private final HttpClient httpClient;
    private final ObjectMapper mapper;
    private final MemoryCache cache;
    private final String apiKey;
    private final String baseUrl;

    public static class Builder {
        private String apiKey = System.getenv("DIVISAS_API_KEY");
        private String baseUrl = "https://api.divisas.lat/v1";
        private long cacheTtlSeconds = 3600;
        private HttpClient customHttpClient;

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder cacheTtlSeconds(long cacheTtlSeconds) {
            this.cacheTtlSeconds = cacheTtlSeconds;
            return this;
        }

        public Builder httpClient(HttpClient httpClient) {
            this.customHttpClient = httpClient;
            return this;
        }

        public DivisasClient build() {
            return new DivisasClient(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public DivisasClient() {
        this(new Builder());
    }

    private DivisasClient(Builder builder) {
        this.apiKey = builder.apiKey;
        this.baseUrl = builder.baseUrl.endsWith("/") ? builder.baseUrl.substring(0, builder.baseUrl.length() - 1) : builder.baseUrl;
        this.cache = new MemoryCache(builder.cacheTtlSeconds);
        this.httpClient = builder.customHttpClient != null ? builder.customHttpClient : HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
                
        this.mapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public QueryBuilder query() {
        return new QueryBuilder(this);
    }

    public java.util.List<lat.divisas.sdk.models.CountryResponse> getCountries() {
        lat.divisas.sdk.models.CountryResponse[] arr = request("/countries", null, lat.divisas.sdk.models.CountryResponse[].class);
        return java.util.Arrays.asList(arr);
    }

    public java.util.List<String> getCurrencies(lat.divisas.sdk.enums.Country country) {
        if (country == null) throw new IllegalArgumentException("Country cannot be null");
        String[] arr = request("/" + country.getCode() + "/currencies", null, String[].class);
        return java.util.Arrays.asList(arr);
    }

    public <T> T request(String endpoint, Map<String, String> queryParams, Class<T> responseType) {
        String queryString = "";
        if (queryParams != null && !queryParams.isEmpty()) {
            queryString = "?" + queryParams.entrySet().stream()
                    .map(e -> URLEncoder.encode(e.getKey(), StandardCharsets.UTF_8) + "=" + URLEncoder.encode(e.getValue(), StandardCharsets.UTF_8))
                    .collect(Collectors.joining("&"));
        }

        String fullUrl = this.baseUrl + (endpoint.startsWith("/") ? endpoint : "/" + endpoint) + queryString;

        T cachedResponse = cache.get(fullUrl);
        if (cachedResponse != null) {
            return cachedResponse;
        }

        HttpRequest.Builder requestBuilder = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .header("Accept", "application/json")
                .header("User-Agent", "DivisasLat-JavaSDK/1.0")
                .GET();

        if (this.apiKey != null && !this.apiKey.trim().isEmpty()) {
            requestBuilder.header("Authorization", "Bearer " + this.apiKey);
        }

        try {
            HttpResponse<String> response = httpClient.send(requestBuilder.build(), HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                try {
                    ErrorResponse err = mapper.readValue(response.body(), ErrorResponse.class);
                    if (err.getError() != null) {
                        throw new DivisasException("API Error: " + response.statusCode() + " - " + err.getError(), response.statusCode());
                    }
                } catch (Exception ignored) {
                }
                throw new DivisasException("API Error: " + response.statusCode() + " - " + response.body(), response.statusCode());
            }

            T result = mapper.readValue(response.body(), responseType);
            cache.set(fullUrl, result);
            return result;

        } catch (DivisasException e) {
            throw e;
        } catch (Exception e) {
            throw new DivisasException("Request failed: " + e.getMessage(), e);
        }
    }
}
