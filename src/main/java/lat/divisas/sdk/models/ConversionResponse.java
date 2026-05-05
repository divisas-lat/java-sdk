package lat.divisas.sdk.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConversionResponse {
    private CurrencyAmount from;
    private CurrencyAmount to;
    private double amount;
    private double result;
    
    @JsonProperty("effective_rate")
    private double effectiveRate;
    
    private String via;
    private String date;
    private String note;

    public CurrencyAmount getFrom() { return from; }
    public CurrencyAmount getTo() { return to; }
    public double getAmount() { return amount; }
    public double getResult() { return result; }
    public double getEffectiveRate() { return effectiveRate; }
    public String getVia() { return via; }
    public String getDate() { return date; }
    public String getNote() { return note; }

    public void setFrom(CurrencyAmount from) { this.from = from; }
    public void setTo(CurrencyAmount to) { this.to = to; }
    public void setAmount(double amount) { this.amount = amount; }
    public void setResult(double result) { this.result = result; }
    public void setEffectiveRate(double effectiveRate) { this.effectiveRate = effectiveRate; }
    public void setVia(String via) { this.via = via; }
    public void setDate(String date) { this.date = date; }
    public void setNote(String note) { this.note = note; }

    public static class CurrencyAmount {
        private String currency;
        private double amount;

        public String getCurrency() { return currency; }
        public double getAmount() { return amount; }

        public void setCurrency(String currency) { this.currency = currency; }
        public void setAmount(double amount) { this.amount = amount; }
    }
}
