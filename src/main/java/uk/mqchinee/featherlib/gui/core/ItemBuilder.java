package uk.mqchinee.featherlib.gui.core;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.mqchinee.featherlib.ext.ItemBuilderInterface;
import uk.mqchinee.featherlib.utils.TextUtils;

import java.util.*;

public class ItemBuilder implements ItemBuilderInterface {
    private final TextUtils t = new TextUtils();
    private ItemStack item;
    private ItemMeta meta;

    public ItemBuilder(Material material) {
        item = new ItemStack(material);
        meta = item.getItemMeta();
    }

    @Override
    public ItemBuilder amount(int integer) {
        item.setAmount(integer);
        return this;
    }

    @Override
    public int getAmount() {
       return item.getAmount();
    }

    @Override
    public ItemBuilder name(String string) {
        meta.setDisplayName(t.colorize(string));
        return this;
    }

    @Override
    public String getName() {
        return meta.getDisplayName();
    }

    @Override
    public ItemBuilder lore(String string) {
        if (!Objects.equals(string, "")) {
            String nw = t.colorize(string);
            String[] lr = nw.split("\n");
            meta.setLore(Arrays.asList(lr));
        }
        return this;
    }

    @Override
    public List<String> getLore() {
        return meta.getLore();
    }

    @Override
    public ItemBuilder flags(ItemFlag... flag) {
        meta.addItemFlags(flag);
        return this;
    }

    @Override
    public ItemBuilder removeFlags(ItemFlag... flag) {
        meta.removeItemFlags(flag);
        return this;
    }

    @Override
    public Set<ItemFlag> getFlags() {
        return meta.getItemFlags();
    }

    @Override
    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    @Override
    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        meta.removeEnchant(enchantment);
        return this;
    }

    @Override
    public Map<Enchantment, Integer> getEnchantments() {
        return meta.getEnchants();
    }

    @Override
    public ItemBuilder defaultFlags() {
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_POTION_EFFECTS);
        return this;
    }

    @Override
    public ItemBuilder unbreakable(boolean bool) {
        meta.setUnbreakable(bool);
        return this;
    }

    @Override
    public ItemBuilder material(Material material) {
        item = new ItemStack(material);
        return this;
    }

    @Override
    public ItemBuilder setMeta(ItemMeta m) {
        meta = m;
        return this;
    }

    @Override
    public ItemBuilder itemstack(ItemStack stack) {
        item = stack;
        return this;
    }

    @Override
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}
