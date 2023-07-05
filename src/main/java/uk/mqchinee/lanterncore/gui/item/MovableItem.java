package uk.mqchinee.lanterncore.gui.item;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import uk.mqchinee.lanterncore.gui.ChestMenu;

import java.util.ArrayList;
import java.util.List;

public class MovableItem extends MenuItem {

    @Getter private final ChestMenu menu;
    @Getter @Setter private int speed;
    @Getter private int total = speed;
    @Getter @Setter private int[] slots;
    @Getter private int current;
    private int n = 0;
    @Getter private String[] structure;
    private final List<Integer> structureSlots = new ArrayList<>();

    private MovableItem(@NonNull ItemStack item, int[] slots, int speed, ChestMenu menu) {
        super(item);
        this.menu = menu;
        this.slots = slots;
        this.current = slots[0];
        this.speed = speed;
    }

    private MovableItem(@NonNull ItemStack item, int speed, ChestMenu menu, String... structure) {
        super(item);
        this.menu = menu;
        this.structure = structure;
        parse();
        this.current = this.slots[0];
        this.speed = speed;
    }

    public void parse() {
            int char_no = 0;
            for(int i = 0; i < menu.getRows(); i++) {
                for(char ch: getStructure()[i].replace(" ", "").toCharArray()) {
                    if (ch == '%') { structureSlots.add(char_no); }
                    char_no++;
                }
            }
        this.slots = (structureSlots.stream().mapToInt(Integer::intValue).toArray());
    }

    @Override
    public boolean update() {
        total--;
        if (total <= 0) {
            n++;
            menu.removeItem(current);
            if (n >= slots.length) {
                n = 0;
            }
            current = slots[n];
            menu.setItem(this, current);
            total = speed;
        }
        return false;
    }

    public static MovableItem create(@NonNull ItemStack item, int[] slots, int speed, ChestMenu menu) {
        return new MovableItem(item, slots, speed, menu);
    }

    public static MovableItem create(@NonNull ItemStack item, int speed, ChestMenu menu, String... structure) {
        return new MovableItem(item, speed, menu, structure);
    }

    @Override
    public MenuItem copy() {
        return create(this.getItem().clone(), getSlots(), getSpeed(), getMenu())
                .setOnPrimary(this.getOnPrimary())
                .setOnMiddle(this.getOnMiddle())
                .setOnSecondary(this.getOnSecondary())
                .setOnShiftPrimary(this.getOnShiftPrimary())
                .setOnDouble(this.getOnDouble())
                .setOnDrop(this.getOnDrop())
                .setOnNumber(this.getOnNumber())
                .setOnShiftSecondary(this.getOnShiftSecondary())
                .setOnClick(this.getOnClick())
                .setOnDropAll(this.getOnDropAll());
    }

}
