package uk.mqchinee.featherlib.deprecated.GUI.buttons;

import org.bukkit.inventory.ItemStack;

@Deprecated
public class Button {

    private ButtonListener listener;
    private ItemStack icon;


    public Button(ItemStack icon){
        this.icon = icon;
    }


    public void setListener(ButtonListener listener) {
        this.listener = listener;
    }


    public Button withListener(ButtonListener listener) {
        this.listener = listener;
        return this;
    }


    public ButtonListener getListener() {
        return listener;
    }


    public ItemStack getIcon() {
        return icon;
    }


    public void setIcon(ItemStack icon) {
        this.icon = icon;
    }

}
