package lat.divisas.sdk.models;

import java.util.Map;

public class ForecastResponse {
    private String country;
    private String baseCurrency;
    private String currency;
    private Map<String, RateData> forecast;

    public String getCountry() { return country; }
    public String getBaseCurrency() { return baseCurrency; }
    public String getCurrency() { return currency; }
    public Map<String, RateData> getForecast() { return forecast; }

    public void setCountry(String country) { this.country = country; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setForecast(Map<String, RateData> forecast) { this.forecast = forecast; }
}
