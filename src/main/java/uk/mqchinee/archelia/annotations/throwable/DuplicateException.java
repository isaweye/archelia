package uk.mqchinee.archelia.annotations.throwable;

public class DuplicateException extends RuntimeException {

    public DuplicateException() {}

    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(Throwable cause) {
        super(cause);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
