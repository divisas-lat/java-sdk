package lat.divisas.sdk.models;

public class StatsResponse {
    private String country;
    private String baseCurrency;
    private String currency;
    private String period;
    private RateData min;
    private RateData max;
    private RateData avg;

    public String getCountry() { return country; }
    public String getBaseCurrency() { return baseCurrency; }
    public String getCurrency() { return currency; }
    public String getPeriod() { return period; }
    public RateData getMin() { return min; }
    public RateData getMax() { return max; }
    public RateData getAvg() { return avg; }

    public void setCountry(String country) { this.country = country; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setPeriod(String period) { this.period = period; }
    public void setMin(RateData min) { this.min = min; }
    public void setMax(RateData max) { this.max = max; }
    public void setAvg(RateData avg) { this.avg = avg; }
}
