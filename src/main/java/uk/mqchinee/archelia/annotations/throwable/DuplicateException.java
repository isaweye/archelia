package uk.mqchinee.archelia.annotations.throwable;

/**
 * Custom exception class to indicate a duplicate item or entry.
 * This exception can be thrown when a duplicate item is detected in a system.
 */
public class DuplicateException extends RuntimeException {

    /**
     * Constructs a new DuplicateException with no specified detail message.
     */
    public DuplicateException() {}

    /**
     * Constructs a new DuplicateException with the specified detail message.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     */
    public DuplicateException(String message) {
        super(message);
    }

    /**
     * Constructs a new DuplicateException with the specified cause.
     *
     * @param cause The cause (which is saved for later retrieval by the getCause() method).
     */
    public DuplicateException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new DuplicateException with the specified detail message and cause.
     *
     * @param message The detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause   The cause (which is saved for later retrieval by the getCause() method).
     */
    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }
}
