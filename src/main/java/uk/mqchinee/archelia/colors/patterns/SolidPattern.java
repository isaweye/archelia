package uk.mqchinee.archelia.colors.patterns;

import uk.mqchinee.archelia.colors.Iridium;

import java.util.regex.Matcher;

/**
 * A pattern implementation for applying solid color effect to text using a specific syntax.
 *
 * @since 1.0
 */
public class SolidPattern implements Pattern {

    private final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<hex:#([0-9A-Fa-f]{3,6})>|#\\{([0-9A-Fa-f]{3,6})}");

    /**
     * Processes the given string and applies solid color effect to matching patterns.
     *
     * @param string The input string to process.
     * @return The processed string with solid color effect applied.
     */
    @Override
    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String color = matcher.group(1);
            if (color == null) color = matcher.group(2);

            string = string.replace(matcher.group(), Iridium.getColor(color) + "");
        }
        return string;
    }

    /**
     * Clears the solid color patterns from the given string.
     *
     * @param string The input string to clear solid color patterns from.
     * @return The string with solid color patterns removed.
     */
    @Override
    public String clear(String string) {
        return process(string);
    }

}
