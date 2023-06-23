package uk.mqchinee.featherlib.gui.icons;

import org.bukkit.inventory.ItemStack;
import uk.mqchinee.featherlib.gui.listeners.ClickListener;
import uk.mqchinee.featherlib.gui.listeners.CloseListener;
import uk.mqchinee.featherlib.gui.listeners.OpenListener;

public interface IconProvider {

    ItemStack getItem();

    Icon setItem(ItemStack item);

    ClickListener getLeftClick();
    ClickListener getRightClick();
    ClickListener getDoubleClick();
    ClickListener getShiftLeftClick();
    ClickListener getShiftRightClick();
    ClickListener getDropClick();
    ClickListener getMiddleClick();
    CloseListener getOnClose();
    OpenListener getOnOpen();

    Icon onMiddleClick(ClickListener listener);
    Icon onLeftClick(ClickListener listener);
    Icon onRightClick(ClickListener listener);
    Icon onDoubleClick(ClickListener listener);
    Icon onShiftLeftClick(ClickListener listener);
    Icon onShiftRightClick(ClickListener listener);
    Icon onDropClick(ClickListener listener);
    Icon onCLose(CloseListener listener);
    Icon onOpen(OpenListener listener);
}
