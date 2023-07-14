package uk.mqchinee.lanterncore.annotations.throwable;

public class CommandInfoException extends RuntimeException {
    public CommandInfoException () {}

    public CommandInfoException (String message) {
        super(message);
    }

    public CommandInfoException (Throwable cause) {
        super(cause);
    }

    public CommandInfoException (String message, Throwable cause) {
        super(message, cause);
    }
}
