package lat.divisas.sdk.enums;

/**
 * Commonly used currencies
 */
public enum Currency {
    USD,
    EUR,
    MXN,
    GTQ,
    HNL,
    CRC,
    NIO,
    DOP,
    SVC;

    public static Currency fromCode(String code) {
        for (Currency currency : values()) {
            if (currency.name().equalsIgnoreCase(code)) {
                return currency;
            }
        }
        throw new IllegalArgumentException("Unsupported currency code: " + code);
    }
}
