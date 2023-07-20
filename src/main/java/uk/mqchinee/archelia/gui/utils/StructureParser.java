package uk.mqchinee.archelia.gui.utils;

import uk.mqchinee.archelia.gui.ChestMenu;
import uk.mqchinee.archelia.gui.PageableChestMenu;
import uk.mqchinee.archelia.gui.item.ClickableItem;
import uk.mqchinee.archelia.gui.item.MenuItem;
import uk.mqchinee.archelia.impl.StructureParserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StructureParser implements StructureParserInterface {

    @Override
    public void parse(ChestMenu menu, String[] structure, Map<Character, MenuItem> map) {
        int char_no = 0;
        for(int i = 0; i < menu.getRows(); i++) {
            for(char ch: structure[i].replace(" ", "").toCharArray()) {
                if (ch != '#') { menu.addItem(map.get(ch), char_no); }
                char_no++;
            }
        }
    }

    @Override
    public void parse(PageableChestMenu menu, String[] structure, Map<Character, MenuItem> map) {
        int char_no = 0;
        List<Integer> pageSlots = new ArrayList<>();
        for(int i = 0; i < menu.getRows(); i++) {
            for(char ch: structure[i].replace(" ", "").toCharArray()) {
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
