package lat.divisas.sdk.builder;

import lat.divisas.sdk.DivisasClient;
import lat.divisas.sdk.enums.Country;
import lat.divisas.sdk.enums.Currency;
import lat.divisas.sdk.models.ConversionResponse;
import lat.divisas.sdk.models.TodayRatesResponse;
import lat.divisas.sdk.models.HistoricalRateResponse;
import lat.divisas.sdk.models.StatsResponse;
import lat.divisas.sdk.models.ForecastResponse;
import lat.divisas.sdk.models.PercentileResponse;

import java.util.HashMap;
import java.util.Map;

public class QueryBuilder {
    private final DivisasClient client;
    private Country country;
    private Currency currency;

    public QueryBuilder(DivisasClient client) {
        this.client = client;
    }

    public QueryBuilder forCountry(Country country) {
        this.country = country;
        return this;
    }

    public QueryBuilder withCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    private String getCountryCode() {
        if (country == null) {
            throw new IllegalStateException("Country is required. Call forCountry() first.");
        }
        return country.getCode();
    }

    public TodayRatesResponse getToday() {
        String endpoint = "/" + getCountryCode() + "/rates";
        if (currency != null) {
            endpoint += "/" + currency.name();
        }
        return client.request(endpoint, null, TodayRatesResponse.class);
    }

    public ConversionResponse convert(Currency targetCurrency, double amount) {
        if (targetCurrency == null) throw new IllegalArgumentException("Target currency is required");
        
        String endpoint = "/" + getCountryCode() + "/rates/convert";
        Map<String, String> query = new HashMap<>();
        query.put("to", targetCurrency.name());
        query.put("amount", String.valueOf(amount));

        if (currency != null) {
            query.put("from", currency.name());
        }

        return client.request(endpoint, query, ConversionResponse.class);
    }
    
    public HistoricalRateResponse getHistory(String fromDate, String toDate) {
        if (currency == null) {
            throw new IllegalStateException("Currency is required for historical data. Call withCurrency() first.");
        }
        
        String endpoint = "/" + getCountryCode() + "/rates/history";
        Map<String, String> query = new HashMap<>();
        query.put("currency", currency.name());
        
        if (fromDate != null && !fromDate.isEmpty()) query.put("from", fromDate);
        if (toDate != null && !toDate.isEmpty()) query.put("to", toDate);

        return client.request(endpoint, query, HistoricalRateResponse.class);
    }

    public StatsResponse getStats(String period) {
        String endpoint = "/" + getCountryCode() + "/rates/stats";
        Map<String, String> query = new HashMap<>();
        if (period != null && !period.isEmpty()) {
            query.put("period", period);
        }
        
        if (currency != null) {
            query.put("currency", currency.name());
        }

        return client.request(endpoint, query, StatsResponse.class);
    }

    public ForecastResponse getForecast(int days) {
        String endpoint = "/" + getCountryCode() + "/rates/forecast";
        Map<String, String> query = new HashMap<>();
        query.put("days", String.valueOf(days));
        
        if (currency != null) {
            query.put("currency", currency.name());
        }

        return client.request(endpoint, query, ForecastResponse.class);
    }

    public PercentileResponse getPercentile(String period) {
        String endpoint = "/" + getCountryCode() + "/rates/percentile";
        Map<String, String> query = new HashMap<>();
        if (period != null && !period.isEmpty()) {
            query.put("period", period);
        }
        
        if (currency != null) {
            query.put("currency", currency.name());
        }

        return client.request(endpoint, query, PercentileResponse.class);
    }
}
