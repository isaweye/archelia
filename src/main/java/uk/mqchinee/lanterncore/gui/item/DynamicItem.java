package uk.mqchinee.lanterncore.gui.item;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import uk.mqchinee.lanterncore.enums.Time;
import uk.mqchinee.lanterncore.utils.RunUtils;

public class DynamicItem extends MenuItem {

    private final ItemStack i;

    private DynamicItem(@NonNull ItemStack item) {
        super(item);
        this.i = item;
    }

    public void replace(ItemStack item, int time, Time type) {
        if (getItem() == i) {
            setItem(item);
            RunUtils.runLater(() -> setItem(i), time, type);
        }
    }

    public static DynamicItem create(@NonNull ItemStack item) {
        return new DynamicItem(item);
    }

    @Override
    public boolean update() {
        return true;
    }

    @Override
    public MenuItem copy() {
        return create(i.clone())
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
