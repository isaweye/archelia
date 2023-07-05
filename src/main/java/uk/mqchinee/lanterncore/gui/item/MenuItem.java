package uk.mqchinee.lanterncore.gui.item;

import lombok.*;
import lombok.experimental.Accessors;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Accessors(chain = true)
public abstract class MenuItem {

    @NonNull @Getter @Setter private ItemStack item;

    //Actions
    @Getter @Setter protected Consumer<InventoryClickEvent> onPrimary = (click) -> {};
    @Getter @Setter protected Consumer<InventoryClickEvent> onMiddle = (click) -> {};
    @Getter @Setter protected Consumer<InventoryClickEvent> onSecondary = (click) -> {};
    @Getter @Setter protected Consumer<InventoryClickEvent> onShiftPrimary = (click) -> {};
    @Getter @Setter protected Consumer<InventoryClickEvent> onDouble = (click) -> {};
    @Getter @Setter protected Consumer<InventoryClickEvent> onDrop = (click) -> {};
    @Getter @Setter protected Consumer<InventoryClickEvent> onShiftSecondary = (click) -> {};
    @Getter @Setter protected Consumer<InventoryClickEvent> onDropAll = (click) -> {};
    @Getter @Setter protected Consumer<InventoryClickEvent> onNumber = (click) -> {};
    @Getter @Setter protected Consumer<InventoryClickEvent> onClick = (click) -> {};

    public boolean update() { return false; }

    public abstract MenuItem copy();

}
