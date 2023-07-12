package uk.mqchinee.lanterncore.gui.utils;

import lombok.Getter;
import lombok.Setter;
import uk.mqchinee.lanterncore.gui.ChestMenu;
import uk.mqchinee.lanterncore.gui.PageableChestMenu;
import uk.mqchinee.lanterncore.gui.item.MenuItem;
import uk.mqchinee.lanterncore.impl.StructureInterface;

import java.util.HashMap;
import java.util.Map;

public class Structure implements StructureInterface {

    @Getter @Setter private String[] structure;
    @Getter @Setter private Map<Character, MenuItem> map;
    @Getter private final StructureParser parser;

    public Structure(String... rows) {
        this.structure = rows;
        this.map = new HashMap<>();
        this.parser = new StructureParser();
    }

    @Override
    public Structure set(char _char, MenuItem item) {
        map.put(_char, item);
        return this;
    }

    @Override
    public void process(PageableChestMenu menu) {
        parser.parse(menu, getStructure(), getMap());
    }

    @Override
    public void process(ChestMenu menu) {
        parser.parse(menu, getStructure(), getMap());
    }


}
