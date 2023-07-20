package uk.mqchinee.archelia.impl;

import uk.mqchinee.archelia.gui.ChestMenu;
import uk.mqchinee.archelia.gui.PageableChestMenu;
import uk.mqchinee.archelia.gui.item.MenuItem;

import java.util.Map;

public interface StructureInterface {
    StructureInterface set(char _char, MenuItem item);

    String[] getStructure();

    void setStructure(String[] structure);

    Map<Character, MenuItem> getMap();

    void setMap(Map<Character, MenuItem> map);

    void process(PageableChestMenu menu);

    void process(ChestMenu menu);

}
