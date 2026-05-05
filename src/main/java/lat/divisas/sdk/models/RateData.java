package lat.divisas.sdk.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RateData {
    @JsonProperty("currency_code")
    private String currencyCode;
    
    private double buy;
    private double sell;

    public String getCurrencyCode() { return currencyCode; }
    public double getBuy() { return buy; }
    public double getSell() { return sell; }

    public void setCurrencyCode(String currencyCode) { this.currencyCode = currencyCode; }
    public void setBuy(double buy) { this.buy = buy; }
    public void setSell(double sell) { this.sell = sell; }
}
