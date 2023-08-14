package uk.mqchinee.archelia.enums;

/**
 * An enumeration representing different time durations.
 * <p>
 * This enum provides options for different time durations and associates each duration with a specific integer value.
 * </p>
 * @since 1.0
 */
public enum Duration {
    /**
     * Represents an infinite duration.
     * The associated integer value is 1000000.
     */
    INFINITY(1000000),

    /**
     * Represents a duration of one second.
     * The associated integer value is 20 (assuming 20 ticks per second in Minecraft).
     */
    SECOND(20);

    /**
     * The integer value associated with the duration.
     */
    public final int value;

    /**
     * Constructor for the Duration enum.
     * @param value The integer value associated with the duration.
     */
    Duration(int value) {
        this.value = value;
    }
}
