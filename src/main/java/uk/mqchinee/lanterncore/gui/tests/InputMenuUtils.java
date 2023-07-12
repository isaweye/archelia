package uk.mqchinee.lanterncore.gui.tests;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.mqchinee.lanterncore.gui.item.ClickableItem;

public class InputMenuUtils {

    public static void add(ClickableItem item, String symbol) {
        update(item, getName(item)+symbol);
    }

    public static void back(ClickableItem item) {
        StringBuilder sb = new StringBuilder(getName(item));
        sb.deleteCharAt(sb.length()-1);
        update(item, sb.toString());
    }

    private static void update(ClickableItem item, String name) {
        ItemStack upd = item.getItem();
        ItemMeta meta = upd.getItemMeta();

        assert meta != null;
        meta.setDisplayName(name);

        upd.setItemMeta(meta);
        item.setItem(upd);
    }

    public static String getName(ClickableItem item) {
        return item.getItem().getItemMeta().getDisplayName();
    }

    public static void clear(ClickableItem item) {
        update(item, " ");
    }

}
