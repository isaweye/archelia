package uk.mqchinee.archelia.gui.item;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * A loopable item in a graphical user interface (GUI).
 * <p>
 * This class extends the {@link MenuItem} class and represents an item in a GUI that can loop through a list of
 * {@link ItemStack} objects and change its appearance after a certain number of ticks. The item will update itself
 * automatically and call a provided {@link Consumer} on each update.
 * </p>
 * @since 1.0
 */
public class LoopableItem extends MenuItem {

    @Getter
    private final List<ItemStack> items;
    private int totalTicks;

    private int tickCount;
    private Consumer<LoopableItem> onUpdate;

    private LoopableItem(@NonNull List<ItemStack> items, int ticks) {
        super(items.get(0));
        this.items = items;
        this.totalTicks = ticks;
        this.tickCount = ticks;
    }

    /**
     * Set the consumer that will be called on each update of the loopable item.
     *
     * @param onUpdate The consumer to be called on each update of the loopable item.
     */
    public void setOnUpdate(Consumer<LoopableItem> onUpdate) {
        this.onUpdate = onUpdate;
    }

    /**
     * Get the consumer that is called on each update of the loopable item.
     *
     * @return The consumer called on each update of the loopable item.
     */
    public Consumer<LoopableItem> getOnUpdate() {
        return onUpdate;
    }

    /**
     * Create a new loopable item with the specified list of items and update delay.
     *
     * @param items The list of {@link ItemStack} objects representing the loopable items.
     * @param ticks The number of ticks between each update of the loopable item.
     * @return A new instance of {@link LoopableItem}.
     */
    public static LoopableItem create(@NonNull List<ItemStack> items, int ticks) {
        return new LoopableItem(items, ticks);
    }

    /**
     * Set the delay between each update of the loopable item.
     *
     * @param delay The number of ticks between each update of the loopable item.
     */
    public void setDelay(int delay) {
        this.totalTicks = delay;
    }

    /**
     * Get the delay between each update of the loopable item.
     *
     * @return The number of ticks between each update of the loopable item.
     */
    public int getDelay() {
        return this.totalTicks;
    }

    /**
     * Update the loopable item based on the tick count and the specified update delay.
     *
     * @return {@code true} if the item was updated, {@code false} otherwise.
     */
    @Override
    public boolean update() {
        tickCount--;
        if (tickCount <= 0) {
            tickCount = totalTicks;

            int itemIndex = items.indexOf(getItem()) + 1;
            if (itemIndex >= items.size()) setItem(items.get(0));
            else setItem(items.get(itemIndex));
            if (onUpdate != null) {
                onUpdate.accept(this);
            }

            return true;
        }

        return false;
    }

    /**
     * Set the current loopable item index.
     *
     * @param index The index of the loopable item to set.
     */
    public void setIndex(int index) {
        setItem(items.get(index));
        if (onUpdate != null) {
            onUpdate.accept(this);
        }
    }

    /**
     * Get the next loopable item in the list based on the specified step.
     *
     * @param step The step size to move to the next item.
     * @return The next loopable item.
     */
    public ItemStack getItemStep(int step) {
        return items.get(items.indexOf(getItem()) + (step));
    }

    /**
     * Create a copy of this loopable item.
     *
     * @return A new instance of {@link LoopableItem} with the same properties as this item.
     */
    @Override
    public MenuItem copy() {
        return create(this.items.stream().map(ItemStack::clone).collect(Collectors.toList()), this.totalTicks)
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
