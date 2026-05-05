package lat.divisas.sdk.models;

import java.util.Map;

public class HistoricalRateResponse {
    private String country;
    private String baseCurrency;
    private String currency;
    private String from;
    private String to;
    private Map<String, RateData> data;

    public String getCountry() { return country; }
    public String getBaseCurrency() { return baseCurrency; }
    public String getCurrency() { return currency; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    public Map<String, RateData> getData() { return data; }

    public void setCountry(String country) { this.country = country; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setFrom(String from) { this.from = from; }
    public void setTo(String to) { this.to = to; }
    public void setData(Map<String, RateData> data) { this.data = data; }
}
