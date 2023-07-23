package uk.mqchinee.archelia.enums;

/**
 * An enumeration representing different options for filtering commands based on the sender type.
 * <p>
 * This enum provides options to filter commands based on whether they can be executed by the console, players, or both.
 * </p>
 */
public enum SenderFilter {
    /**
     * Represents that the command can only be executed by the console.
     */
    CONSOLE,

    /**
     * Represents that the command can only be executed by players.
     */
    PLAYER,

    /**
     * Represents that the command can be executed by both the console and players.
     */
    BOTH
}
