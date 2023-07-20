package uk.mqchinee.archelia.gui.item;

import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import uk.mqchinee.archelia.gui.ChestMenu;

public class DynamicItem extends MenuItem {

    private final ItemStack i;
    private final ChestMenu menu;

    private DynamicItem(@NonNull ItemStack item, ChestMenu menu) {
        super(item);
        this.menu = menu;
        this.i = item;
    }

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

    public static DynamicItem create(@NonNull ItemStack item, ChestMenu menu) {
        return new DynamicItem(item, menu);
    }

    @Override
    public boolean update() {
        return true;
    }

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
