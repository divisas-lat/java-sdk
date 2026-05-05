package lat.divisas.sdk.enums;

/**
 * Supported countries in Divisas.lat
 */
public enum Country {
    GUATEMALA("GT"),
    HONDURAS("HN"),
    EL_SALVADOR("SV"),
    COSTA_RICA("CR"),
    NICARAGUA("NI"),
    MEXICO("MX"),
    REPUBLICA_DOMINICANA("DO");

    private final String code;

    Country(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Country fromCode(String code) {
        for (Country country : values()) {
            if (country.getCode().equalsIgnoreCase(code)) {
                return country;
            }
        }
        throw new IllegalArgumentException("Unsupported country code: " + code);
    }
}
