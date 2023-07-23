package uk.mqchinee.archelia.impl;

import uk.mqchinee.archelia.gui.ChestMenu;
import uk.mqchinee.archelia.gui.PageableChestMenu;
import uk.mqchinee.archelia.gui.item.MenuItem;

import java.util.Map;

public interface StructureParserInterface {

    /**
     * Parses and applies the structure to a ChestMenu.
     *
     * @param menu      The ChestMenu to apply the structure to.
     * @param structure The structure as an array of strings.
     * @param map       The map of characters to MenuItem objects in the structure.
     */
    void parse(ChestMenu menu, String[] structure, Map<Character, MenuItem> map);

    /**
     * Parses and applies the structure to a PageableChestMenu.
     *
     * @param menu      The PageableChestMenu to apply the structure to.
     * @param structure The structure as an array of strings.
     * @param map       The map of characters to MenuItem objects in the structure.
     */
    void parse(PageableChestMenu menu, String[] structure, Map<Character, MenuItem> map);
}
