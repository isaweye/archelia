package uk.mqchinee.lanterncore.gui.item;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class ClickableItem extends MenuItem {

    private final boolean update;

    private ClickableItem(@NonNull ItemStack item, boolean update) {
        super(item);
        this.update = update;
    }

    public static ClickableItem create(@NonNull ItemStack item, boolean update) {
        return new ClickableItem(item, update);
    }

    @Override
    public boolean update() {
        return this.update;
    }

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
