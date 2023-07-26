package uk.mqchinee.archelia.gui.item;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * An item in a graphical user interface (GUI) that can be toggled between two states (enabled and disabled).
 * <p>
 * This class represents an item in a GUI that can be toggled between two states: enabled and disabled. It provides methods
 * to update the state of the item and allows setting custom ItemStacks for both enabled and disabled states. The class also
 * supports automatically toggling the item's state on click, depending on the specified configuration.
 * </p>
 * @since 1.0
 */
public class ToggleableItem extends MenuItem {

    @Getter
    @Setter
    private Consumer<ToggleableItem> onUpdate;
    @Getter
    @Setter
    private ItemStack enabled;
    @Getter
    @Setter
    private ItemStack disabled;
    @Getter
    @Setter
    private ItemStack last = new ItemStack(Material.AIR);
    private final boolean toggleOnClick;

    private ToggleableItem(@NonNull ItemStack e, @NonNull ItemStack d, boolean toggleOnClick) {
        super(e);
        this.toggleOnClick = toggleOnClick;
        setEnabled(e);
        setDisabled(d);
        if (toggleOnClick) {
            this.setOnClick(i -> toggle());
        }
    }

    @Override
    public boolean update() {
        return true;
    }

    /**
     * Create a toggleable item with custom enabled and disabled states and toggle configuration.
     * <p>
     * This method creates a toggleable item with custom ItemStacks representing the enabled and disabled states. The
     * toggleOnClick parameter determines whether the item should be automatically toggled when clicked. If toggleOnClick is
     * set to true, the item's state will change between enabled and disabled every time it is clicked.
     * </p>
     *
     * @param e            The ItemStack representing the enabled state of the item.
     * @param d            The ItemStack representing the disabled state of the item.
     * @param toggleOnClick Whether the item should be automatically toggled when clicked.
     * @return The created ToggleableItem instance.
     */
    public static ToggleableItem create(@NonNull ItemStack e, @NonNull ItemStack d, boolean toggleOnClick) {
        return new ToggleableItem(e, d, toggleOnClick);
    }

    /**
     * Check if the item is currently in the enabled state.
     *
     * @return true if the item is enabled, false otherwise.
     */
    public boolean enabled() {
        return getItem() == getEnabled();
    }

    /**
     * Toggle the state of the item between enabled and disabled.
     * <p>
     * This method toggles the state of the item between enabled and disabled. If the item is currently in the enabled state,
     * it will be changed to the disabled state, and vice versa. If an onUpdate Consumer is set, it will be called after the
     * item's state is toggled.
     * </p>
     */
    public void toggle() {
        if (enabled()) {
            setItem(getDisabled());
            return;
        }
        setItem(getEnabled());
        if (onUpdate != null) {
            onUpdate.accept(this);
        }
    }

    /**
     * Set the state of the item explicitly.
     * <p>
     * This method allows setting the state of the item explicitly. If the state parameter is true, the item will be set to
     * the enabled state; otherwise, it will be set to the disabled state. If an onUpdate Consumer is set, it will be called
     * after the item's state is updated.
     * </p>
     *
     * @param state The state to set (true for enabled, false for disabled).
     */
    public void toggle(boolean state) {
        if (state) {
            setItem(getEnabled());
            return;
        }
        setItem(getDisabled());
        if (onUpdate != null) {
            onUpdate.accept(this);
        }
    }

    @Override
    public MenuItem copy() {
        return create(getEnabled().clone(), getDisabled().clone(), toggleOnClick)
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
