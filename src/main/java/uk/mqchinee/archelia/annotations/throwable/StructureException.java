package uk.mqchinee.archelia.annotations.throwable;


public class StructureException extends RuntimeException {
    public StructureException () {}

    public StructureException (String message) {
        super(message);
    }

    public StructureException (Throwable cause) {
        super(cause);
    }

    public StructureException (String message, Throwable cause) {
        super(message, cause);
    }
}
