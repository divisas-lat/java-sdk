package lat.divisas.sdk.models;

public class PercentileResponse {
    private String country;
    private String baseCurrency;
    private String currency;
    private String period;
    private double percentile;

    public String getCountry() { return country; }
    public String getBaseCurrency() { return baseCurrency; }
    public String getCurrency() { return currency; }
    public String getPeriod() { return period; }
    public double getPercentile() { return percentile; }

    public void setCountry(String country) { this.country = country; }
    public void setBaseCurrency(String baseCurrency) { this.baseCurrency = baseCurrency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public void setPeriod(String period) { this.period = period; }
    public void setPercentile(double percentile) { this.percentile = percentile; }
}
