package uk.mqchinee.featherlib.gui.toolbar;

import uk.mqchinee.featherlib.gui.icons.Icon;
import uk.mqchinee.featherlib.gui.inventory.GUI;

import java.util.HashMap;

public class ToolbarBuilder {

    private final HashMap<Integer, Icon> icons = new HashMap<>();
    private final GUI gui;

    public ToolbarBuilder(GUI gui) {
        this.gui = gui;
    }

    public GUI get() {
        return gui;
    }

    public ToolbarBuilder setIcon(int slot, Icon icon) {
        icons.put(slot, icon);
        return this;
    }

    public Toolbar build() {
        return new Toolbar(icons);
    }

}
