package uk.mqchinee.lanterncore.colors.patterns;

import net.md_5.bungee.api.ChatColor;
import uk.mqchinee.lanterncore.colors.Iridium;

import java.awt.*;
import java.util.regex.Matcher;

public class GradientPattern implements Pattern {

    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<gradient:#([0-9A-Fa-f]{6})>(.*?)</gradient:#([0-9A-Fa-f]{6})>");

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
