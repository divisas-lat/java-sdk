package lat.divisas.sdk.exceptions;

public class DivisasException extends RuntimeException {
    private final int statusCode;

    public DivisasException(String message) {
        super(message);
        this.statusCode = 0;
    }

    public DivisasException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public DivisasException(String message, Throwable cause) {
        super(message, cause);
        this.statusCode = 0;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
