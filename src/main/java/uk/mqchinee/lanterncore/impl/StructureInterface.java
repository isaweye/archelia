package uk.mqchinee.lanterncore.impl;

import uk.mqchinee.lanterncore.gui.ChestMenu;
import uk.mqchinee.lanterncore.gui.PageableChestMenu;
import uk.mqchinee.lanterncore.gui.item.MenuItem;

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
