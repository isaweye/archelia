package uk.mqchinee.lanterncore.converters.options;

import uk.mqchinee.lanterncore.converters.JsonMessageConverter;

public class JsonMessageOptions {
    public JsonMessageOptions hover(boolean hover) {
        this.hover = hover;
        return this;
    }

    public JsonMessageOptions suggest(boolean suggest) {
        this.suggest = suggest;
        return this;
    }

    public JsonMessageOptions run(boolean run) {
        this.run = run;
        return this;
    }

    public JsonMessageOptions iridium(boolean iridium) {
        this.iridium = iridium;
        return this;
    }

    public boolean iridium() {
        return this.iridium;
    }

    public JsonMessageOptions link(boolean link) {
        this.link = link;
        return this;
    }

    public JsonMessageOptions translate(boolean translate) {
        this.translate = translate;
        return this;
    }

    private boolean hover = true;

    private boolean iridium = true;

    public boolean hover() {
        return this.hover;
    }

    private boolean suggest = true;

    public boolean suggest() {
        return this.suggest;
    }

    private boolean run = true;

    public boolean run() {
        return this.run;
    }

    private boolean link = true;

    public boolean link() {
        return this.link;
    }

    private boolean translate = true;

    public boolean translate() {
        return this.translate;
    }


    public JsonMessageConverter create() {
        if (this.hover && this.run && this.suggest && this.link && this.translate && this.iridium)
            return JsonMessageConverter.DEFAULT;
        return new JsonMessageConverter(this.hover, this.run, this.suggest, this.link, this.translate, this.iridium);
    }
}
