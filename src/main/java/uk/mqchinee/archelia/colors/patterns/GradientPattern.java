package uk.mqchinee.archelia.colors.patterns;

import net.md_5.bungee.api.ChatColor;
import uk.mqchinee.archelia.colors.Iridium;

import java.awt.*;
import java.util.regex.Matcher;

/**
 * A pattern implementation for applying gradient color to text using a specific syntax.
 *
 * @since 1.0
 */
public class GradientPattern implements Pattern {

    private final java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<gradient:#([0-9A-Fa-f]{6})>(.*?)</gradient:#([0-9A-Fa-f]{6})>");

    /**
     * Processes the given string and applies gradient color to matching patterns.
     *
     * @param string The input string to process.
     * @return The processed string with gradient color applied.
     */
    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String start = matcher.group(1);
            String end = matcher.group(3);
            String content = matcher.group(2);
            string = string.replace(matcher.group(), Iridium.color(content, ChatColor.of(new Color(Integer.parseInt(start, 16))).getColor(), new Color(Integer.parseInt(end, 16))));
        }
        return string;
    }

    /**
     * Clears the gradient patterns from the given string.
     *
     * @param string The input string to clear gradient patterns from.
     * @return The string with gradient patterns removed.
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
