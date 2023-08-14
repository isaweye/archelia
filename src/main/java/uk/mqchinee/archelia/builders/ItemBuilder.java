package uk.mqchinee.archelia.builders;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.mqchinee.archelia.impl.Builder;
import uk.mqchinee.archelia.utils.TextUtils;

import java.util.*;

/**
 * A builder class for creating and customizing ItemStack objects in a Bukkit environment.
 * @since 1.0
 */
public class ItemBuilder implements Builder<ItemStack> {

    private ItemStack item;
    private ItemMeta meta;

    /**
     * Constructs an ItemBuilder with the specified material.
     *
     * @param material The material of the ItemStack to be created.
     */
    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();
    }

    /**
     * Constructs an ItemBuilder with an existing ItemStack.
     *
     * @param item The existing ItemStack to be customized.
     */
    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    /**
     * Constructs an ItemBuilder with the default material (STONE).
     */
    public ItemBuilder() {
        this.item = new ItemStack(Material.STONE);
        this.meta = item.getItemMeta();
    }

    public ItemBuilder amount(int integer) {
        item.setAmount(integer);
        return this;
    }

    public int getAmount() {
        return item.getAmount();
    }

    public ItemBuilder name(String string) {
        meta.setDisplayName(TextUtils.colorize(string));
        return this;
    }

    public String getName() {
        return meta.getDisplayName();
    }

    public ItemBuilder lore(String string) {
        if (!Objects.equals(string, "")) {
            String nw = TextUtils.colorize(string);
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

    @Override
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}
