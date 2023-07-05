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
    @Getter @Setter private ItemStack last;
    private final boolean toggleOnClick;

    private ToggleableItem(@NonNull ItemStack e, @NonNull ItemStack d, boolean toggleOnClick) {
        super(e);
        this.toggleOnClick = toggleOnClick;
        setEnabled(e); setDisabled(d);
        if (toggleOnClick) { this.setOnClick(i -> toggle()); }
    }

    @Override
    public boolean update() {
        return getItem() != getLast();
    }

    public static ToggleableItem create(@NonNull ItemStack e, @NonNull ItemStack d, boolean toggleOnClick) {
        return new ToggleableItem(e, d, toggleOnClick);
    }

    public boolean enabled() {
        return getItem() == getEnabled();
    }

    public void toggle() {
        if (enabled()) { setItem(getDisabled()); setLast(getDisabled()); return; }
        setItem(getEnabled()); setLast(getEnabled());
        onUpdate.accept(this);
    }

    public void toggle(boolean state) {
        if (state) { setItem(getEnabled()); setLast(getEnabled()); return; }
        setItem(getDisabled()); setLast(getDisabled());
        onUpdate.accept(this);
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
                .setOnDropAll(this.getOnDropAll());
    }
}