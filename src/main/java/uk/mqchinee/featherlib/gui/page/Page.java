package uk.mqchinee.featherlib.gui.page;

import uk.mqchinee.featherlib.gui.icons.Icon;
import uk.mqchinee.featherlib.gui.inventory.GUI;
import uk.mqchinee.featherlib.gui.toolbar.Toolbar;

import java.util.NavigableMap;
import java.util.TreeMap;

public class Page {

    private final NavigableMap<Integer, Icon> icons = new TreeMap<>();
    private final int size;
    private final int number;
    private final Toolbar toolbar;
    private final int start;

    public Page(int start, int size, int number, Toolbar toolbar) {
        this.number = number;
        this.size = size*9;
        this.toolbar = toolbar;
        this.start = start;
        createToolbar();
    }

    public NavigableMap<Integer, Icon> getIcons() {
        return icons;
    }

    private void createToolbar() {
        if (toolbar != null) {
            toolbar.getIcons().forEach((this::setIcon));
        }
    }

    public Icon getIcon(int slot) {
        if(slot <= size) {
            return icons.get(slot);
        }
        return null;
    }

    public Integer getLastIconSlot() {
        return icons.lastKey();
    }

    public Icon getLastIcon() {
        return icons.lastEntry().getValue();
    }

    public void addIcon(Icon icon) {
        if (icons.isEmpty()) {
            icons.put(start, icon);
        }
        int lastIndex = icons.lastKey()+1;
        if(lastIndex <= size) {
            icons.put(lastIndex, icon);
        }
    }

    public void addIcon(Icon icon, GUI gui) {
        int lastIndex = icons.lastKey()+1;
        if(lastIndex <= size) {
            icons.put(lastIndex, icon);
        } else {
            if (gui.getPage(number+1) != null) {
                gui.getPage(number+1).addIcon(icon);
            }
            else { gui.addPage(new Page(start, size, number+1, toolbar)).addIcon(icon); }
        }
    }

    public void setIcon(int slot, Icon icon) {
        if(icons.get(slot) != null) {
            icons.replace(slot, icon);
            return;
        }
        icons.put(slot, icon);
    }

}
