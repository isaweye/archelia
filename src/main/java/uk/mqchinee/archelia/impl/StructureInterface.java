package uk.mqchinee.archelia.impl;

import uk.mqchinee.archelia.gui.ChestMenu;
import uk.mqchinee.archelia.gui.PageableChestMenu;
import uk.mqchinee.archelia.gui.item.MenuItem;

import java.util.Map;

public interface StructureInterface {

    /**
     * Sets the item associated with a character in the structure.
     *
     * @param _char The character in the structure.
     * @param item  The item to set for the character.
     * @return The StructureInterface instance for method chaining.
     */
    StructureInterface set(char _char, MenuItem item);

    /**
     * Gets the structure as an array of strings.
     *
     * @return The structure as an array of strings.
     */
    String[] getStructure();

    /**
     * Sets the structure as an array of strings.
     *
     * @param structure The structure to set.
     */
    void setStructure(String[] structure);

    /**
     * Gets the map of characters to items in the structure.
     *
     * @return The map of characters to items.
     */
    Map<Character, MenuItem> getMap();

    /**
     * Sets the map of characters to items in the structure.
     *
     * @param map The map to set.
     */
    void setMap(Map<Character, MenuItem> map);

    /**
     * Processes the structure with a PageableChestMenu.
     *
     * @param menu The PageableChestMenu to process the structure with.
     */
    void process(PageableChestMenu menu);

    /**
     * Processes the structure with a ChestMenu.
     *
     * @param menu The ChestMenu to process the structure with.
     */
    void process(ChestMenu menu);
}
