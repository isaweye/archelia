package uk.mqchinee.archelia.converters.options;

import uk.mqchinee.archelia.converters.JsonMessageConverter;

/**
 * An options builder for configuring the behavior of a {@link JsonMessageConverter}.
 * This class allows enabling or disabling specific features of the JSON message.
 * @since 1.0
 */
public class JsonMessageOptions {

    // Default values for options
    private boolean hover = true;
    private boolean iridium = true;
    private boolean suggest = true;
    private boolean run = true;
    private boolean link = true;
    private boolean translate = true;

    /**
     * Sets whether the hover event should be enabled in the JSON message.
     *
     * @param hover True to enable the hover event, false to disable.
     * @return This {@link JsonMessageOptions} instance for method chaining.
     */
    public JsonMessageOptions hover(boolean hover) {
        this.hover = hover;
        return this;
    }

    /**
     * Sets whether the suggest command event should be enabled in the JSON message.
     *
     * @param suggest True to enable the suggest command event, false to disable.
     * @return This {@link JsonMessageOptions} instance for method chaining.
     */
    public JsonMessageOptions suggest(boolean suggest) {
        this.suggest = suggest;
        return this;
    }

    /**
     * Sets whether the run command event should be enabled in the JSON message.
     *
     * @param run True to enable the run command event, false to disable.
     * @return This {@link JsonMessageOptions} instance for method chaining.
     */
    public JsonMessageOptions run(boolean run) {
        this.run = run;
        return this;
    }

    /**
     * Sets whether the iridium option should be enabled in the JSON message.
     *
     * @param iridium True to enable the iridium option, false to disable.
     * @return This {@link JsonMessageOptions} instance for method chaining.
     */
    public JsonMessageOptions iridium(boolean iridium) {
        this.iridium = iridium;
        return this;
    }

    /**
     * Gets the value of the iridium option.
     *
     * @return True if the iridium option is enabled, false otherwise.
     */
    public boolean iridium() {
        return this.iridium;
    }

    /**
     * Sets whether the link event should be enabled in the JSON message.
     *
     * @param link True to enable the link event, false to disable.
     * @return This {@link JsonMessageOptions} instance for method chaining.
     */
    public JsonMessageOptions link(boolean link) {
        this.link = link;
        return this;
    }

    /**
     * Sets whether the "translate" event should be enabled in the JSON message.
     *
     * @param translate True to enable the "translate" event, false to disable.
     * @return This {@link JsonMessageOptions} instance for method chaining.
     */
    public JsonMessageOptions translate(boolean translate) {
        this.translate = translate;
        return this;
    }

    /**
     * Creates a new {@link JsonMessageConverter} instance based on the configured options.
     * If all options (hover, run, suggest, link, translate, and iridium) are set to true,
     * the default {@link JsonMessageConverter} instance is returned.
     *
     * @return A new {@link JsonMessageConverter} instance with the configured options.
     */
    public JsonMessageConverter create() {
        if (this.hover && this.run && this.suggest && this.link && this.translate && this.iridium) {
            return JsonMessageConverter.DEFAULT;
        }
        return new JsonMessageConverter(this.hover, this.run, this.suggest, this.link, this.translate, this.iridium);
    }
}
