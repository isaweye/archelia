package uk.mqchinee.featherapi.economy;

/**
 * Indicates a typical Return for an Economy method.  
 * It includes a {@link ResponseType} indicating whether the plugin currently being used for Economy actually allows
 * the method, or if the operation was a success or failure.
 *
 */
public class EconomyResponse {

    /**
     * Enum for types of Responses indicating the status of a method call.
     */
    public static enum ResponseType {
        SUCCESS(1),
        FAILURE(2),
        NOT_IMPLEMENTED(3);

        private int id;

        ResponseType(int id) {
            this.id = id;
        }

        int getId() {
            return id;
        }
    }

    /**
     * Amount modified by calling method
     */
    public final double amount;
    /**
     * New balance of account
     */
    public final double balance;
    /**
     * Success or failure of call. Using Enum of ResponseType to determine valid
     * outcomes
     */
    public final ResponseType type;
    /**
     * Error message if the variable 'type' is ResponseType.FAILURE
     */
    public final String errorMessage;

    /**
     * Constructor for EconomyResponse
     * @param amount Amount modified during operation
     * @param balance New balance of account
     * @param type Success or failure type of the operation
     * @param errorMessage Error message if necessary (commonly null)
     */
    public EconomyResponse(double amount, double balance, ResponseType type, String errorMessage) {
        this.amount = amount;
        this.balance = balance;
        this.type = type;
        this.errorMessage = errorMessage;
    }

    /**
     * Checks if an operation was successful
     * @return Value
     */
    public boolean transactionSuccess() {
        return type == ResponseType.SUCCESS;
    }
}
