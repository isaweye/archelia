package uk.mqchinee.featherlib.gui.toolbar;

import uk.mqchinee.featherlib.gui.icons.Icon;

import java.util.Map;

public class Toolbar {

    private final Map<Integer, Icon> toolbarIcons;

    public Toolbar(Map<Integer, Icon> icons) {
        this.toolbarIcons = icons;
    }

    public Map<Integer, Icon> getIcons() {
        return toolbarIcons;
    }

}
