package uk.mqchinee.lanterncore.colors.patterns;


import uk.mqchinee.lanterncore.colors.Iridium;

import java.util.regex.Matcher;

public class RainbowPattern implements Pattern {

    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("<rainbow([0-9]{1,3})>(.*?)</rainbow>");

    public String process(String string) {
        Matcher matcher = pattern.matcher(string);
        while (matcher.find()) {
            String saturation = matcher.group(1);
            String content = matcher.group(2);
            string = string.replace(matcher.group(), Iridium.rainbow(content, Float.parseFloat(saturation)));
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
