package uk.mqchinee.featherlib.gui.icons;

import org.bukkit.inventory.ItemStack;
import uk.mqchinee.featherlib.gui.listeners.ClickListener;
import uk.mqchinee.featherlib.gui.listeners.CloseListener;
import uk.mqchinee.featherlib.gui.listeners.OpenListener;

public class Icon implements IconProvider {

    ClickListener left;
    ClickListener right;
    ClickListener drop;
    ClickListener _double;
    ClickListener shiftLeft;
    ClickListener shiftRight;
    ClickListener middle;
    OpenListener open;
    CloseListener close;
    ItemStack item;

    public Icon(ItemStack itemStack) {
        this.item = itemStack;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public Icon setItem(ItemStack item) {
        this.item = item;
        return this;
    }

    @Override
    public ClickListener getLeftClick() {
        return left;
    }

    @Override
    public ClickListener getRightClick() {
        return right;
    }

    @Override
    public ClickListener getDoubleClick() {
        return _double;
    }

    @Override
    public ClickListener getShiftLeftClick() {
        return shiftLeft;
    }

    @Override
    public ClickListener getShiftRightClick() {
        return shiftRight;
    }

    @Override
    public ClickListener getDropClick() {
        return drop;
    }

    @Override
    public ClickListener getMiddleClick() {
        return middle;
    }

    @Override
    public CloseListener getOnClose() {
        return close;
    }

    @Override
    public OpenListener getOnOpen() {
        return open;
    }

    @Override
    public Icon onMiddleClick(ClickListener listener) {
        this.middle = listener;
        return this;
    }

    @Override
    public Icon onLeftClick(ClickListener listener) {
        this.left = listener;
        return this;
    }

    @Override
    public Icon onRightClick(ClickListener listener) {
        this.right = listener;
        return this;
    }

    @Override
    public Icon onDoubleClick(ClickListener listener) {
        this._double = listener;
        return this;
    }

    @Override
    public Icon onShiftLeftClick(ClickListener listener) {
        this.shiftLeft = listener;
        return this;
    }

    @Override
    public Icon onShiftRightClick(ClickListener listener) {
        this.shiftRight = listener;
        return this;
    }

    @Override
    public Icon onDropClick(ClickListener listener) {
        this.drop = listener;
        return this;
    }

    @Override
    public Icon onCLose(CloseListener listener) {
        this.close = listener;
        return this;
    }

    @Override
    public Icon onOpen(OpenListener listener) {
        this.open = listener;
        return this;
    }
}
