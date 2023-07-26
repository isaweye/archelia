package uk.mqchinee.archelia.annotations.throwable;

/**
 * Exception class used for signaling errors related to data structure.
 */
public class StructureException extends RuntimeException {

    /**
     * Constructs a new empty StructureException.
     * @since 1.0
     */
    public StructureException() {}

    /**
     * Constructs a new StructureException with the specified error message.
     *
     * @param message The error message explaining the cause of the exception.
     */
    public StructureException(String message) {
        super(message);
    }

    /**
     * Constructs a new StructureException with the specified cause.
     *
     * @param cause The cause of the exception, usually another throwable.
     */
    public StructureException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new StructureException with the specified error message and cause.
     *
     * @param message The error message explaining the cause of the exception.
     * @param cause   The cause of the exception, usually another throwable.
     */
    public StructureException(String message, Throwable cause) {
        super(message, cause);
    }
}
