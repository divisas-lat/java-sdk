package lat.divisas.sdk.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class TodayRatesResponse {
    private String country;
    
    @JsonProperty("base_currency")
    private String baseCurrency;
    
    private String date;
    private String source;
    private boolean cached;
    private RateData rate;
    private Map<String, RateData> rates;

    // Getters
    public String getCountry() { return country; }
    public String getBaseCurrency() { return baseCurrency; }
    public String getDate() { return date; }
    public String getSource() { return source; }
    public boolean isCached() { return cached; }
    public RateData getRate() { return rate; }
    public Map<String, RateData> getRates() { return rates; }
    
    // Setters (Jackson needs them or a @JsonCreator)
    public void setCountry(String country) { this.country = country; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }
    public void setDate(String date) { this.date = date; }
    public void setSource(String source) { this.source = source; }
    public void setCached(boolean cached) { this.cached = cached; }
    public void setRate(RateData rate) { this.rate = rate; }
    public void setRates(Map<String, RateData> rates) { this.rates = rates; }
}
