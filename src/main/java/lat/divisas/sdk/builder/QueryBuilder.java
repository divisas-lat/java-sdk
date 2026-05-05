package lat.divisas.sdk.builder;

import lat.divisas.sdk.DivisasClient;
import lat.divisas.sdk.enums.Country;
import lat.divisas.sdk.enums.Currency;
import lat.divisas.sdk.models.ConversionResponse;
import lat.divisas.sdk.models.TodayRatesResponse;

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
    
    // Additional methods (stats, history, forecast) omitted here for brevity 
    // but follow the exact same pattern!
}
