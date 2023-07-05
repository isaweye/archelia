package uk.mqchinee.lanterncore.gui.item;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class ClickableItem extends MenuItem {

    private ClickableItem(@NonNull ItemStack item) {
        super(item);
    }

    public static ClickableItem create(@NonNull ItemStack item) {
        return new ClickableItem(item);
    }

    @Override
    public MenuItem copy() {
        return create(this.getItem().clone())
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
