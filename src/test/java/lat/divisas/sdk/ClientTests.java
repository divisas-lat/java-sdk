package lat.divisas.sdk;

import lat.divisas.sdk.enums.Country;
import lat.divisas.sdk.enums.Currency;
import lat.divisas.sdk.exceptions.DivisasException;
import lat.divisas.sdk.models.ConversionResponse;
import lat.divisas.sdk.models.TodayRatesResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClientTests {

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockResponse;

    private DivisasClient client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        client = DivisasClient.builder()
                .httpClient(mockHttpClient)
                .apiKey("test-key")
                .build();
    }

    @Test
    void testGetToday_Success() throws Exception {
        String json = "{\n" +
                "  \"country\": \"GT\",\n" +
                "  \"base_currency\": \"GTQ\",\n" +
                "  \"date\": \"2026-05-05\",\n" +
                "  \"rate\": {\n" +
                "    \"currency_code\": \"USD\",\n" +
                "    \"buy\": 7.63,\n" +
                "    \"sell\": 7.63\n" +
                "  }\n" +
                "}";

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(json);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        TodayRatesResponse res = client.query().forCountry(Country.GUATEMALA).getToday();

        assertNotNull(res);
        assertEquals("GT", res.getCountry());
        assertEquals(7.63, res.getRate().getBuy());
    }

    @Test
    void testConvert_Success() throws Exception {
        String json = "{\n" +
                "  \"from\": {\"currency\": \"USD\", \"amount\": 100},\n" +
                "  \"to\": {\"currency\": \"GTQ\", \"amount\": 763},\n" +
                "  \"amount\": 100,\n" +
                "  \"result\": 763,\n" +
                "  \"effective_rate\": 7.63\n" +
                "}";

        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(json);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        ConversionResponse res = client.query()
                .forCountry(Country.GUATEMALA)
                .withCurrency(Currency.USD)
                .convert(Currency.GTQ, 100);

        assertNotNull(res);
        assertEquals(763, res.getResult());
        assertEquals(7.63, res.getEffectiveRate());
    }

    @Test
    void testApiError_ThrowsException() throws Exception {
        String json = "{\"error\": \"API key required\", \"docs\": \"https://divisas.lat\"}";

        when(mockResponse.statusCode()).thenReturn(401);
        when(mockResponse.body()).thenReturn(json);
        when(mockHttpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class)))
                .thenReturn(mockResponse);

        DivisasException ex = assertThrows(DivisasException.class, () -> {
            client.query().forCountry(Country.GUATEMALA).getToday();
        });

        assertEquals(401, ex.getStatusCode());
        assertTrue(ex.getMessage().contains("API key required"));
    }
}
