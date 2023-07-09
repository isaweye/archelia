package uk.mqchinee.lanterncore.gui.bin;

import lombok.Getter;
import lombok.Setter;
import uk.mqchinee.lanterncore.gui.PageableChestMenu;
import uk.mqchinee.lanterncore.gui.item.ClickableItem;
import uk.mqchinee.lanterncore.gui.item.MenuItem;
import uk.mqchinee.lanterncore.impl.StructureInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputStructure implements StructureInterface {

    @Getter @Setter private String[] structure;
    @Getter @Setter private Map<Character, MenuItem> map;
    @Getter @Setter private List<Integer> pageSlots = new ArrayList<>();

    public InputStructure(String... rows) {
        this.structure = rows;
        this.map = new HashMap<>();
    }

    @Override
    public InputStructure set(char _char, MenuItem item) {
        map.put(_char, item);
        return this;
    }

    @Override
    public MenuItem get(char _char) {
        return map.get(_char);
    }

    /**
     *
     *  TODO: parser
     *
     */

    @Override
    public void parse(PageableChestMenu menu) {
        int char_no = 0;
        for(int i = 0; i < menu.getRows(); i++) {
            for(char ch: getStructure()[i].replace(" ", "").toCharArray()) {
                if (ch != '#') {
                    if (ch == '%') { pageSlots.add(char_no); }
                    else if (ch == '<') { menu.setPreviousPageItem((ClickableItem) get(ch), char_no); }
                    else if (ch == '>') { menu.setNextPageItem((ClickableItem) get(ch), char_no); }
                    else {
                        menu.addItem(get(ch), char_no);
                    }
                }
                char_no++;
            }
        }
        menu.setItemSlots(pageSlots.stream().mapToInt(Integer::intValue).toArray());
    }
}
