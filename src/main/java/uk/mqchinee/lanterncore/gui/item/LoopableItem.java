package uk.mqchinee.lanterncore.gui.item;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class LoopableItem extends MenuItem {

    @Getter private final List<ItemStack> items;
    private final int totalTicks;

    private int tickCount;

    private LoopableItem(@NonNull List<ItemStack> items, int ticks) {
        super(items.get(0));
        this.items = items;
        this.totalTicks = ticks;
        this.tickCount = ticks;
    }

    public static LoopableItem create(@NonNull List<ItemStack> items, int ticks) {
        return new LoopableItem(items, ticks);
    }

    @Override
    public boolean update() {
        tickCount--;
        if(tickCount <= 0) {
            tickCount = totalTicks;

            int itemIndex = items.indexOf(getItem()) + 1;
            if(itemIndex >= items.size()) setItem(items.get(0));
            else setItem(items.get(itemIndex));

            return true;
        }

        return false;
    }

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
                .setOnDropAll(this.getOnDropAll());
    }

}
