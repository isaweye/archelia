package uk.mqchinee.archelia.converters;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.checkerframework.checker.nullness.qual.NonNull;
import uk.mqchinee.archelia.colors.Iridium;
import uk.mqchinee.archelia.converters.options.JsonMessageOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("deprecation")
public final class JsonMessageConverter {

    /**
     * Creates a new JsonMessageConverter with the specified options.
     *
     * @param hover          Whether hover events are enabled.
     * @param run            Whether run events are enabled.
     * @param suggest        Whether suggest events are enabled.
     * @param link           Whether link events are enabled.
     * @param translateCodes Whether color code translation is enabled.
     * @param iridium        Whether Iridium color code processing is enabled.
     */
    public JsonMessageConverter(boolean hover, boolean run, boolean suggest, boolean link, boolean translateCodes, boolean iridium) {
        this.hover = hover;
        this.run = run;
        this.suggest = suggest;
        this.link = link;
        this.translateCodes = translateCodes;
        this.iridium = iridium;
    }

    /**
     * A pre-configured JsonMessageConverter instance with all options enabled by default.
     */
    public static final JsonMessageConverter DEFAULT = new JsonMessageConverter(true, true, true, true, true, true);

    private static final Pattern JMM_PATTERN = Pattern.compile("\\<(.+?)\\>\\<(.+?)\\>", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern JMM_ARG_SPLIT_PATTERN = Pattern.compile(" | ", Pattern.CASE_INSENSITIVE | Pattern.LITERAL);

    private final boolean hover;
    private final boolean run;
    private final boolean iridium;
    private final boolean suggest;
    private final boolean link;
    private final boolean translateCodes;

    /**
     * Converts text to an array of BaseComponent.
     *
     * @param input The text message to convert.
     * @return An array of BaseComponent representing the converted message.
     */
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

    /**
     * Converts messages with JSON-like syntax to an array of BaseComponent.
     *
     * @param input The message with syntax to convert.
     * @return An array of BaseComponent representing the converted message.
     */
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

    /**
     * Provides a way to obtain a JsonMessageOptions instance for configuring conversion options.
     *
     * @return A new JsonMessageOptions instance.
     */
    public static JsonMessageOptions options() {
        return new JsonMessageOptions();
    }
}