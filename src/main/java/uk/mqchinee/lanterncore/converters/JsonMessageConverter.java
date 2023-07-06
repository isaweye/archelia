package uk.mqchinee.lanterncore.converters;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.checkerframework.checker.nullness.qual.NonNull;
import uk.mqchinee.lanterncore.colors.Iridium;
import uk.mqchinee.lanterncore.converters.options.JsonMessageOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public final class JsonMessageConverter {

    public JsonMessageConverter(boolean hover, boolean run, boolean suggest, boolean link, boolean translateCodes, boolean iridium) {
        this.hover = hover;
        this.run = run;
        this.suggest = suggest;
        this.link = link;
        this.translateCodes = translateCodes;
        this.iridium = iridium;
    }

    public static final JsonMessageConverter DEFAULT = new JsonMessageConverter(true, true, true, true, true, true);
    private static final Pattern JMM_PATTERN = Pattern.compile("\\<(.+?)\\>\\<(.+?)\\>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern JMM_ARG_SPLIT_PATTERN = Pattern.compile(" | ", Pattern.CASE_INSENSITIVE | Pattern.LITERAL);

    private final boolean hover;

    private final boolean run;

    private final boolean iridium;

    private final boolean suggest;

    private final boolean link;

    private final boolean translateCodes;

    @NonNull
    public BaseComponent[] plainConvert(@NonNull String input) {
        input = input.replace("\\n", "\n");
        if (iridium) {
            input = Iridium.clearThat(input);
        }
        if (translateCodes) {
            input = ChatColor.translateAlternateColorCodes('&', input);
        }
        List<BaseComponent> components = new ArrayList<>();
        final Matcher matcher = JMM_PATTERN.matcher(input);
        int lastEnd = 0;
        while (matcher.find()) {
            final String argsStr = matcher.group(1);
            final String text = matcher.group(2);
            final String before = input.substring(lastEnd, matcher.start());
            components.addAll(Arrays.asList(TextComponent.fromLegacyText(before)));
            final String[] args = JMM_ARG_SPLIT_PATTERN.split(argsStr);
            final TextComponent txt = new TextComponent(TextComponent.fromLegacyText(text));
            for (String arg : args) {
                final int i = arg.indexOf('=');
                final String opt = arg.substring(0, i).toLowerCase();
                final String val = arg.substring(i + 1);

                switch (opt) {
                    case "hover" -> {
                        if (hover) {
                            txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(val)));
                        }
                    }
                    case "suggest" -> {
                        if (suggest) {
                            txt.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, val));
                        }
                    }
                    case "run" -> {
                        if (run) {
                            txt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, val));
                        }
                    }
                    case "link" -> {
                        if (link) {
                            txt.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, val));
                        }
                    }
                }
            }
            components.add(txt);

            lastEnd = matcher.end();
        }
        if (lastEnd < (input.length())) {
            final String after = input.substring(lastEnd);
            components.addAll(Arrays.asList(TextComponent.fromLegacyText(after)));
        }
        return components.toArray(new BaseComponent[0]);
    }

    @NonNull
    public BaseComponent[] convert(@NonNull String input) {
        input = input.replace("\\n", "\n");
        if (iridium) {
            input = Iridium.process(input);
        }
        if (translateCodes) {
            input = ChatColor.translateAlternateColorCodes('&', input);
        }
        List<BaseComponent> components = new ArrayList<>();
        final Matcher matcher = JMM_PATTERN.matcher(input);
        int lastEnd = 0;
        while (matcher.find()) {
            final String argsStr = matcher.group(1);
            final String text = matcher.group(2);
            final String before = input.substring(lastEnd, matcher.start());
            components.addAll(Arrays.asList(TextComponent.fromLegacyText(before)));
            final String[] args = JMM_ARG_SPLIT_PATTERN.split(argsStr);
            final TextComponent txt = new TextComponent(TextComponent.fromLegacyText(text));
            for (String arg : args) {
                final int i = arg.indexOf('=');
                final String opt = arg.substring(0, i).toLowerCase();
                final String val = arg.substring(i + 1);

                switch (opt) {
                    case "hover" -> {
                        if (hover) {
                            txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, TextComponent.fromLegacyText(val)));
                        }
                    }
                    case "suggest" -> {
                        if (suggest) {
                            txt.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, val));
                        }
                    }
                    case "run" -> {
                        if (run) {
                            txt.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, val));
                        }
                    }
                    case "link" -> {
                        if (link) {
                            txt.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, val));
                        }
                    }
                }
            }
            components.add(txt);

            lastEnd = matcher.end();
        }
        if (lastEnd < (input.length())) {
            final String after = input.substring(lastEnd);
            components.addAll(Arrays.asList(TextComponent.fromLegacyText(after)));
        }
        return components.toArray(new BaseComponent[0]);
    }

    public static JsonMessageOptions options() {
        return new JsonMessageOptions();
    }
}
