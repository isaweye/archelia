package uk.mqchinee.archelia.gui.utils;

import lombok.Getter;
import lombok.Setter;
import uk.mqchinee.archelia.annotations.InventoryStructure;
import uk.mqchinee.archelia.annotations.throwable.StructureException;
import uk.mqchinee.archelia.gui.ChestMenu;
import uk.mqchinee.archelia.gui.PageableChestMenu;
import uk.mqchinee.archelia.gui.item.MenuItem;
import uk.mqchinee.archelia.impl.StructureInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * A utility class representing the structure of a graphical user interface (GUI) menu.
 * <p>
 * This class represents the structure of a GUI menu and provides methods for setting the structure and mapping characters
 * to corresponding menu items. It also supports parsing the structure and adding the mapped menu items to the associated
 * GUI menu.
 * </p>
 * @since 1.0
 */
public class Structure implements StructureInterface {

    @Getter
    @Setter
    private String[] structure;
    @Getter
    @Setter
    private Map<Character, MenuItem> map;
    @Getter
    private final StructureParser parser;

    /**
     * Create a new Structure instance with the specified rows.
     * <p>
     * This constructor creates a new Structure instance with the specified rows. It initializes the internal map of characters
     * to menu items and the StructureParser used for parsing the structure.
     * </p>
     *
     * @param rows The rows of the GUI menu structure.
     */
    public Structure(String... rows) {
        this.structure = rows;
        this.map = new HashMap<>();
        this.parser = new StructureParser();
    }

    /**
     * Get a Structure instance based on the InventoryStructure annotation of a class.
     * <p>
     * This method retrieves a Structure instance based on the InventoryStructure annotation of the specified class. If the
     * annotation is present, the method creates a new Structure instance with the structure specified in the annotation
     * value. If the annotation is missing, a StructureException is thrown.
     * </p>
     *
     * @param clazz The class containing the InventoryStructure annotation.
     * @return The Structure instance with the specified structure.
     * @throws StructureException If the InventoryStructure annotation is missing from the class.
     */
    public static Structure getFromAnnotation(Class clazz) {
        if (clazz.isAnnotationPresent(InventoryStructure.class)) {
            InventoryStructure structure = (InventoryStructure) clazz.getAnnotation(InventoryStructure.class);
            return new Structure(structure.value());
        }
        throw new StructureException("Unable to get structure because annotation is missing.");
    }

    /**
     * Set a mapping between a character and a menu item.
     * <p>
     * This method sets a mapping between the specified character and the given menu item. The character represents a position
     * in the GUI menu structure, and the associated menu item will be placed in that position when the structure is processed.
     * </p>
     *
     * @param _char The character representing the position in the structure.
     * @param item  The menu item to be placed in the specified position.
     * @return The Structure instance with the updated mapping.
     */
    @Override
    public Structure set(char _char, MenuItem item) {
        map.put(_char, item);
        return this;
    }

    /**
     * Process the structure and add mapped menu items to the associated PageableChestMenu.
     * <p>
     * This method processes the structure and adds the mapped menu items to the associated PageableChestMenu. The menu items
     * are placed in their respective positions in the menu based on the structure and mapping.
     * </p>
     *
     * @param menu The PageableChestMenu to which the mapped menu items will be added.
     */
    @Override
    public void process(PageableChestMenu menu) {
        parser.parse(menu, getStructure(), getMap());
    }

    /**
     * Process the structure and add mapped menu items to the associated ChestMenu.
     * <p>
     * This method processes the structure and adds the mapped menu items to the associated ChestMenu. The menu items are placed
     * in their respective positions in the menu based on the structure and mapping.
     * </p>
     *
     * @param menu The ChestMenu to which the mapped menu items will be added.
     */
    @Override
    public void process(ChestMenu menu) {
        parser.parse(menu, getStructure(), getMap());
    }
}
