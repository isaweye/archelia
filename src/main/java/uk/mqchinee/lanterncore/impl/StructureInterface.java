package uk.mqchinee.lanterncore.impl;

import uk.mqchinee.lanterncore.gui.PageableChestMenu;
import uk.mqchinee.lanterncore.gui.item.MenuItem;

import java.util.List;
import java.util.Map;

public interface StructureInterface {
    StructureInterface set(char _char, MenuItem item);

    MenuItem get(char _char);

    String[] getStructure();

    void setStructure(String[] structure);

    Map<Character, MenuItem> getMap();

    void setMap(Map<Character, MenuItem> map);

    List<Integer> getPageSlots();

    void setPageSlots(List<Integer> pageSlots);

    void parse(PageableChestMenu menu);
}
