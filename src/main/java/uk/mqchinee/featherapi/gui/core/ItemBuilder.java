package uk.mqchinee.featherapi.gui.core;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.mqchinee.featherapi.utils.TextUtils;

import java.util.*;

public class ItemBuilder {
    private final TextUtils t = new TextUtils();
    private ItemStack item;
    private ItemMeta meta;

    public ItemBuilder(Material material) {
        item = new ItemStack(material);
        meta = item.getItemMeta();
    }

    public ItemBuilder amount(int integer) {
        item.setAmount(integer);
        return this;
    }

    public int getAmount() {
       return item.getAmount();
    }

    public ItemBuilder name(String string) {
        meta.setDisplayName(t.colorize(string));
        return this;
    }

    public String getName() {
        return meta.getDisplayName();
    }

    public ItemBuilder lore(String string) {
        if (!Objects.equals(string, "")) {
            String nw = t.colorize(string);
            String[] lr = nw.split("\n");
            meta.setLore(Arrays.asList(lr));
        }
        return this;
    }

    public List<String> getLore() {
        return meta.getLore();
    }

    public ItemBuilder flags(ItemFlag... flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder removeFlags(ItemFlag... flag) {
        meta.removeItemFlags(flag);
        return this;
    }

    public Set<ItemFlag> getFlags() {
        return meta.getItemFlags();
    }

    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        meta.removeEnchant(enchantment);
        return this;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return meta.getEnchants();
    }

    public ItemBuilder defaultFlags() {
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_POTION_EFFECTS);
        return this;
    }

    public ItemBuilder unbreakable(boolean bool) {
        meta.setUnbreakable(bool);
        return this;
    }

    public ItemBuilder material(Material material) {
        item = new ItemStack(material);
        return this;
    }

    public ItemBuilder setMeta(ItemMeta m) {
        meta = m;
        return this;
    }

    public ItemBuilder itemstack(ItemStack stack) {
        item = stack;
        return this;
    }


    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}
