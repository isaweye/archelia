package uk.mqchinee.archelia.gui.utils;

import uk.mqchinee.archelia.gui.ChestMenu;
import uk.mqchinee.archelia.gui.PageableChestMenu;
import uk.mqchinee.archelia.gui.item.ClickableItem;
import uk.mqchinee.archelia.gui.item.MenuItem;
import uk.mqchinee.archelia.impl.StructureParserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A utility class that implements the StructureParserInterface to parse the structure of a GUI menu.
 * <p>
 * This class provides methods for parsing the structure of a GUI menu and adding the mapped menu items to the associated
 * ChestMenu and PageableChestMenu. It processes the structure and places the corresponding menu items in their respective
 * positions in the menu.
 * </p>
 * @since 1.0
 */
public class StructureParser implements StructureParserInterface {

    /**
     * Parse the structure and add mapped menu items to the associated ChestMenu.
     * <p>
     * This method parses the structure and adds the mapped menu items to the associated ChestMenu. It processes the structure
     * row by row, placing the corresponding menu items in their respective positions in the menu.
     * </p>
     *
     * @param menu      The ChestMenu to which the mapped menu items will be added.
     * @param structure The structure of the GUI menu.
     * @param map       The mapping of characters to menu items.
     */
    @Override
    public void parse(ChestMenu menu, String[] structure, Map<Character, MenuItem> map) {
        int char_no = 0;
        for (int i = 0; i < menu.getRows(); i++) {
            for (char ch : structure[i].replace(" ", "").toCharArray()) {
                if (ch != '#') {
                    menu.addItem(map.get(ch), char_no);
                }
                char_no++;
            }
        }
    }

    /**
     * Parse the structure and add mapped menu items to the associated PageableChestMenu.
     * <p>
     * This method parses the structure and adds the mapped menu items to the associated PageableChestMenu. It processes
     * the structure row by row, placing the corresponding menu items in their respective positions in the menu. It also
     * sets the previous and next page items based on the mapped menu items with '%' and '<', '>' characters, respectively.
     * </p>
     *
     * @param menu      The PageableChestMenu to which the mapped menu items will be added.
     * @param structure The structure of the GUI menu.
     * @param map       The mapping of characters to menu items.
     */
    @Override
    public void parse(PageableChestMenu menu, String[] structure, Map<Character, MenuItem> map) {
        int char_no = 0;
        List<Integer> pageSlots = new ArrayList<>();
        for (int i = 0; i < menu.getRows(); i++) {
            for (char ch : structure[i].replace(" ", "").toCharArray()) {
                if (ch != '#') {
                    switch (ch) {
                        case '%' -> pageSlots.add(char_no);
                        case '<' -> menu.setPreviousPageItem((ClickableItem) map.get(ch), char_no);
                        case '>' -> menu.setNextPageItem((ClickableItem) map.get(ch), char_no);
                        default -> menu.addItem(map.get(ch), char_no);
                    }
                }
                char_no++;
            }
        }
        menu.setItemSlots(pageSlots.stream().mapToInt(Integer::intValue).toArray());
    }

}
