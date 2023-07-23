package uk.mqchinee.archelia.gui.item;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

/**
 * A clickable item in a graphical user interface (GUI).
 * <p>
 * This class extends the {@link MenuItem} class and represents an item that can be clicked in a GUI.
 * It provides additional functionality for handling click events on the item.
 * </p>
 */
public class ClickableItem extends MenuItem {

    private final boolean update;

    private ClickableItem(@NonNull ItemStack item, boolean update) {
        super(item);
        this.update = update;
    }

    /**
     * Create a new clickable item with the specified item stack and update flag.
     *
     * @param item   The item stack representing the clickable item.
     * @param update Whether the item should update when clicked.
     * @return A new instance of {@link ClickableItem}.
     */
    public static ClickableItem create(@NonNull ItemStack item, boolean update) {
        return new ClickableItem(item, update);
    }

    /**
     * Check if the item should update when clicked.
     *
     * @return {@code true} if the item should update when clicked, {@code false} otherwise.
     */
    @Override
    public boolean update() {
        return this.update;
    }

    /**
     * Create a copy of this clickable item.
     *
     * @return A new instance of {@link ClickableItem} with the same properties as this item.
     */
    @Override
    public MenuItem copy() {
        return create(this.getItem().clone(), this.update)
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
