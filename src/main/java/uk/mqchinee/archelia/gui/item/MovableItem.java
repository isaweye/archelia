package uk.mqchinee.archelia.gui.item;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;
import uk.mqchinee.archelia.gui.ChestMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * An item in a graphical user interface (GUI) that can move and change its position within the GUI.
 * <p>
 * This class represents an item in a GUI that has the ability to move within the GUI and change its position. It provides
 * methods to update the item's position at a specified speed, and also supports reversing the movement. The item can be
 * moved through a sequence of slots defined by the user or based on a structure string provided by the user. The class also
 * allows setting a background item that replaces the current item slot during the movement.
 * </p>
 * @since 1.0
 */
public class MovableItem extends MenuItem {

    @Getter
    private final ChestMenu menu;
    @Getter
    @Setter
    private int speed;
    @Getter
    private int total = speed;
    @Getter
    @Setter
    private int[] slots;
    @Getter
    private int current;
    private int n = 0;
    @Getter
    @Setter
    private MenuItem background = null;
    @Getter
    private String[] structure;
    private final List<Integer> structureSlots = new ArrayList<>();
    private final boolean reverse;
    @Getter
    @Setter
    private Consumer<MovableItem> onReverse;

    private MovableItem(@NonNull ItemStack item, int[] slots, int speed, ChestMenu menu, boolean reverse) {
        super(item);
        this.menu = menu;
        this.slots = slots;
        this.current = slots[0];
        this.speed = speed;
        this.reverse = reverse;
    }

    private MovableItem(@NonNull ItemStack item, int speed, ChestMenu menu, boolean reverse, boolean path, String... structure) {
        super(item);
        this.menu = menu;
        this.structure = structure;
        parse(path);
        this.current = this.slots[0];
        this.speed = speed;
        this.reverse = reverse;
    }

    /**
     * Parse the structure string and convert it into an array of slots.
     * <p>
     * This method is used to parse the structure string provided by the user and convert it into an array of slots to define
     * the movement sequence of the item. The structure string is used to create a grid-based layout for the GUI, where each
     * character represents a cell in the grid. The '%' character is used to indicate the positions where the item should be
     * placed. The method calculates the slots based on the structure and stores them in the slots array.
     * </p>
     */
    public void parse(boolean path) {
        if (!path) {
            int char_no = 0;
            for (int i = 0; i < menu.getRows(); i++) {
                for (char ch : getStructure()[i].replace(" ", "").toCharArray()) {
                    if (ch == '%') {
                        structureSlots.add(char_no);
                    }
                    char_no++;
                }
            }
            this.slots = (structureSlots.stream().mapToInt(Integer::intValue).toArray());
        } else {
            this.slots = new int[]{};
            for (int i = 0; i < menu.getRows(); i++) {
                for (String str : getStructure()[i].split(" ")) {
                    if (!Objects.equals(str, "#")) {
                        this.slots[Integer.parseInt(str)] = Integer.parseInt(str);
                    }
                }
            }
        }

    }

    @Override
    public boolean update() {
        total--;
        if (total <= 0) {
            n++;
            if (getBackground() == null) {
                menu.removeItem(current);
            } else {
                menu.setItem(background, current);
            }
            if (n == slots.length) {
                if (reverse) {
                    this.slots = IntStream.rangeClosed(1, slots.length).map(i -> slots[slots.length - i]).toArray();
                    if (getOnReverse() != null) {
                        onReverse.accept(this);
                    }
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

    /**
     * Create a movable item with custom slots and movement speed.
     * <p>
     * This method creates a movable item with custom slots and movement speed. The item will move through the provided slots
     * in the order specified by the array. The movement speed determines the rate at which the item updates its position in
     * the GUI.
     * </p>
     *
     * @param item   The ItemStack representing the item.
     * @param slots  An array of slots representing the movement sequence of the item.
     * @param speed  The movement speed of the item.
     * @param menu   The ChestMenu containing the item.
     * @param reverse Whether the item's movement should be reversed after completing one cycle.
     * @return The created MovableItem instance.
     */
    public static MovableItem create(@NonNull ItemStack item, int[] slots, int speed, ChestMenu menu, boolean reverse) {
        return new MovableItem(item, slots, speed, menu, reverse);
    }

    /**
     * Create a movable item with custom structure and movement speed.
     * <p>
     * This method creates a movable item with custom structure and movement speed. The structure string is used to create a
     * grid-based layout for the GUI, where each character represents a cell in the grid. The '%' character is used to
     * indicate the positions where the item should be placed. The movement speed determines the rate at which the item updates
     * its position in the GUI.
     * </p>
     *
     * @param item      The ItemStack representing the item.
     * @param speed     The movement speed of the item.
     * @param menu      The ChestMenu containing the item.
     * @param reverse   Whether the item's movement should be reversed after completing one cycle.
     * @param structure The structure string representing the grid-based layout of the GUI.
     * @return The created MovableItem instance.
     */
    public static MovableItem create(@NonNull ItemStack item, int speed, ChestMenu menu, boolean reverse, boolean path, String... structure) {
        return new MovableItem(item, speed, menu, reverse, path, structure);
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
