package uk.mqchinee.archelia.colors.patterns;

/**
 * An interface for defining patterns to process and modify strings.
 *
 * @since 1.0
 */
public interface Pattern {

    /**
     * Processes the given string according to the defined pattern.
     *
     * @param string The input string to process.
     * @return The processed string.
     */
    String process(String string);

    /**
     * Clears any pattern-related modifications from the given string.
     *
     * @param string The input string to clear pattern-related modifications from.
     * @return The string with pattern-related modifications removed.
     */
    String clear(String string);

}
