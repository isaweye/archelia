package uk.mqchinee.lanterncore.gui.item;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ToggleableItem extends MenuItem {

    @Getter @Setter private Consumer<ToggleableItem> onUpdate;
    @Getter @Setter private ItemStack enabled;
    @Getter @Setter private ItemStack disabled;

    private ToggleableItem(@NonNull ItemStack e, @NonNull ItemStack d) {
        super(e);
        setEnabled(e); setDisabled(d);
    }

    public static ToggleableItem create(@NonNull ItemStack e, @NonNull ItemStack d) {
        return new ToggleableItem(e, d);
    }

    public boolean enabled() {
        return getItem() == getEnabled();
    }

    public void toggle() {
        if (enabled()) { setItem(getDisabled()); return; }
        setItem(getEnabled());
    }

    public void toggle(boolean state) {
        if (state) { setItem(getEnabled()); return; }
        setItem(getDisabled());
    }

    @Override
    public MenuItem copy() {
        return create(this.getItem().clone(), getDisabled())
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
