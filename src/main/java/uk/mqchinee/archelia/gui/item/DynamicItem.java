package uk.mqchinee.archelia.gui.item;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import uk.mqchinee.archelia.gui.ChestMenu;

/**
 * A dynamic item in a graphical user interface (GUI).
 * <p>
 * This class extends the {@link MenuItem} class and represents an item in a GUI that can change dynamically over time.
 * It provides methods to replace the item with another item after a specified number of ticks.
 * </p>
 */
public class DynamicItem extends MenuItem {

    private final ItemStack i;
    private final ChestMenu menu;

    private DynamicItem(@NonNull ItemStack item, ChestMenu menu) {
        super(item);
        this.menu = menu;
        this.i = item;
    }

    /**
     * Replace the dynamic item with another item after a specified number of ticks.
     *
     * @param item  The new item stack to replace the dynamic item with.
     * @param ticks The number of ticks after which the replacement should occur.
     */
    public void replace(ItemStack item, int ticks) {
        if (getItem() == i) {
            setItem(item);
            new BukkitRunnable() {
                @Override
                public void run() {
                    setItem(i);
                }
            }.runTaskLater(menu.getPlugin(), ticks);
        }
    }

    /**
     * Create a new dynamic item with the specified item stack and associated chest menu.
     *
     * @param item The item stack representing the dynamic item.
     * @param menu The chest menu associated with the dynamic item.
     * @return A new instance of {@link DynamicItem}.
     */
    public static DynamicItem create(@NonNull ItemStack item, ChestMenu menu) {
        return new DynamicItem(item, menu);
    }

    /**
     * Check if the item should update when clicked.
     *
     * @return {@code true} if the item should update when clicked, {@code false} otherwise.
     */
    @Override
    public boolean update() {
        return true;
    }

    /**
     * Create a copy of this dynamic item.
     *
     * @return A new instance of {@link DynamicItem} with the same properties as this item.
     */
    @Override
    public MenuItem copy() {
        return create(i.clone(), menu)
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
