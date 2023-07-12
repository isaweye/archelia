package uk.mqchinee.lanterncore.impl;

import uk.mqchinee.lanterncore.gui.ChestMenu;
import uk.mqchinee.lanterncore.gui.PageableChestMenu;
import uk.mqchinee.lanterncore.gui.item.MenuItem;

import java.util.Map;

public interface StructureParserInterface {
    void parse(ChestMenu menu, String[] structure, Map<Character, MenuItem> map);

    void parse(PageableChestMenu menu, String[] structure, Map<Character, MenuItem> map);
}
