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

public class Structure implements StructureInterface {

    @Getter @Setter private String[] structure;
    @Getter @Setter private Map<Character, MenuItem> map;
    @Getter private final StructureParser parser;

    public Structure(String... rows) {
        this.structure = rows;
        this.map = new HashMap<>();
        this.parser = new StructureParser();
    }

    public static Structure getFromAnnotation(Class clazz) {
        if (clazz.isAnnotationPresent(InventoryStructure.class)) {
            InventoryStructure structure = (InventoryStructure) clazz.getAnnotation(InventoryStructure.class);
            return new Structure(structure.value());
        }
        throw new StructureException("Unable to get structure because annotation is missing.");
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
