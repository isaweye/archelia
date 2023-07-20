package uk.mqchinee.archelia.impl;

import uk.mqchinee.archelia.gui.ChestMenu;
import uk.mqchinee.archelia.gui.PageableChestMenu;
import uk.mqchinee.archelia.gui.item.MenuItem;

import java.util.Map;

public interface StructureParserInterface {
    void parse(ChestMenu menu, String[] structure, Map<Character, MenuItem> map);

    void parse(PageableChestMenu menu, String[] structure, Map<Character, MenuItem> map);
}
