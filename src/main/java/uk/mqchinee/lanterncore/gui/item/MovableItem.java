package uk.mqchinee.lanterncore.gui.item;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import uk.mqchinee.lanterncore.gui.ChestMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class MovableItem extends MenuItem {

    @Getter private final ChestMenu menu;
    @Getter @Setter private int speed;
    @Getter private int total = speed;
    @Getter @Setter private int[] slots;
    @Getter private int current;
    private int n = 0;
    @Getter @Setter private MenuItem background = null;
    @Getter private String[] structure;
    private final List<Integer> structureSlots = new ArrayList<>();
    private final boolean reverse;

    private MovableItem(@NonNull ItemStack item, int[] slots, int speed, ChestMenu menu, boolean reverse) {
        super(item);
        this.menu = menu;
        this.slots = slots;
        this.current = slots[0];
        this.speed = speed;
        this.reverse = reverse;
    }

    private MovableItem(@NonNull ItemStack item, int speed, ChestMenu menu, boolean reverse, String... structure) {
        super(item);
        this.menu = menu;
        this.structure = structure;
        parse();
        this.current = this.slots[0];
        this.speed = speed;
        this.reverse = reverse;
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
            if (getBackground() == null) { menu.removeItem(current); }
            else { menu.setItem(background, current); }
            if (n == slots.length) {
                if (reverse) {
                    this.slots = IntStream.rangeClosed(1, slots.length).map(i -> slots[slots.length-i]).toArray();
                }
                n = 0;
            }
            current = slots[n];
            menu.setItem(this, current);
            total = speed;
            return true;
        }
        return false;
    }

    public static MovableItem create(@NonNull ItemStack item, int[] slots, int speed, ChestMenu menu, boolean reverse) {
        return new MovableItem(item, slots, speed, menu, reverse);
    }

    public static MovableItem create(@NonNull ItemStack item, int speed, ChestMenu menu, boolean reverse, String... structure) {
        return new MovableItem(item, speed, menu, reverse, structure);
    }

    @Override
    public MenuItem copy() {
        return create(this.getItem().clone(), getSlots(), getSpeed(), getMenu(), this.reverse)
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
