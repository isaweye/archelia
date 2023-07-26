package uk.mqchinee.archelia.annotations.throwable;

/**
 * Exception class used for signaling errors related to command information.
 * @since 1.0
 */
public class CommandInfoException extends RuntimeException {

    /**
     * Constructs a new empty CommandInfoException.
     */
    public CommandInfoException() {}

    /**
     * Constructs a new CommandInfoException with the specified error message.
     *
     * @param message The error message explaining the cause of the exception.
     */
    public CommandInfoException(String message) {
        super(message);
    }

    /**
     * Constructs a new CommandInfoException with the specified cause.
     *
     * @param cause The cause of the exception, usually another throwable.
     */
    public CommandInfoException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new CommandInfoException with the specified error message and cause.
     *
     * @param message The error message explaining the cause of the exception.
     * @param cause   The cause of the exception, usually another throwable.
     */
    public CommandInfoException(String message, Throwable cause) {
        super(message, cause);
    }
}
