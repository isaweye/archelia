package uk.mqchinee.archelia.gui.item;

import lombok.*;
import lombok.experimental.Accessors;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * An abstract item in a graphical user interface (GUI) with various click actions.
 * <p>
 * This class represents an item in a GUI with various click actions. It is an abstract class and provides methods to set
 * different click actions on the item. The class also has a method to update the item and a method to create a copy of the
 * item with the same properties.
 * </p>
 * @since 1.0
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Accessors(chain = true)
public abstract class MenuItem {

    @NonNull
    @Getter
    @Setter
    private ItemStack item;

    // Actions
    @Getter
    @Setter
    protected Consumer<InventoryClickEvent> onPrimary = (click) -> {};
    @Getter
    @Setter
    protected Consumer<InventoryClickEvent> onMiddle = (click) -> {};
    @Getter
    @Setter
    protected Consumer<InventoryClickEvent> onSecondary = (click) -> {};
    @Getter
    @Setter
    protected Consumer<InventoryClickEvent> onShiftPrimary = (click) -> {};
    @Getter
    @Setter
    protected Consumer<InventoryClickEvent> onDouble = (click) -> {};
    @Getter
    @Setter
    protected Consumer<InventoryClickEvent> onDrop = (click) -> {};
    @Getter
    @Setter
    protected Consumer<InventoryClickEvent> onShiftSecondary = (click) -> {};
    @Getter
    @Setter
    protected Consumer<InventoryClickEvent> onDropAll = (click) -> {};
    @Getter
    @Setter
    protected Consumer<InventoryClickEvent> onNumber = (click) -> {};
    @Getter
    @Setter
    protected Consumer<InventoryClickEvent> onClick = (click) -> {};

    /**
     * Update the item.
     * <p>
     * This method is intended to be overridden by subclasses to update the item's properties or behavior based on the
     * specific implementation.
     * </p>
     *
     * @return {@code true} if the item was updated, {@code false} otherwise.
     */
    public boolean update() {
        return false;
    }

    /**
     * Create a copy of this item.
     * <p>
     * This method is intended to be overridden by subclasses to create a copy of the item with the same properties. This
     * allows for maintaining separate instances of the item in the GUI.
     * </p>
     *
     * @return A new instance of the subclass with the same properties as this item.
     */
    public abstract MenuItem copy();

}
