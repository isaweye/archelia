package uk.mqchinee.archelia.colors.patterns;

import uk.mqchinee.archelia.colors.Iridium;

import java.util.regex.Matcher;

/**
 * A pattern implementation for applying rainbow color effect to text using a specific syntax.
 *
 * @since 1.0
 */
public class RainbowPattern implements Pattern {

    private final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<rainbow([0-9]{1,3})>(.*?)</rainbow>");

    /**
     * Processes the given string and applies rainbow color effect to matching patterns.
     *
     * @param string The input string to process.
     * @return The processed string with rainbow color effect applied.
     */
    @Override
    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String saturation = matcher.group(1);
            String content = matcher.group(2);
            string = string.replace(matcher.group(), Iridium.rainbow(content, Float.parseFloat(saturation)));
        }
        return string;
    }

    /**
     * Clears the rainbow patterns from the given string.
     *
     * @param string The input string to clear rainbow patterns from.
     * @return The string with rainbow patterns removed.
     */
    @Override
    public String clear(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String content = matcher.group(2);
            string = string.replace(matcher.group(), content);
        }
        return string;
    }

}
