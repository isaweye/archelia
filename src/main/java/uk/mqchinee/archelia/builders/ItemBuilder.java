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
 *
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

    /**
     * Sets the amount of items in the ItemStack.
     *
     * @param amount The amount to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    /**
     * Retrieves the amount of items in the ItemStack.
     *
     * @return The amount of items.
     */
    public int getAmount() {
        return item.getAmount();
    }

    /**
     * Sets the display name of the ItemStack.
     *
     * @param displayName The display name to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder name(String displayName) {
        meta.setDisplayName(TextUtils.colorize(displayName));
        return this;
    }

    /**
     * Retrieves the display name of the ItemStack.
     *
     * @return The display name.
     */
    public String getName() {
        return meta.getDisplayName();
    }

    /**
     * Sets the lore of the ItemStack.
     *
     * @param lore The lore to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder lore(String lore) {
        if (!Objects.equals(lore, "")) {
            String coloredLore = TextUtils.colorize(lore);
            String[] loreLines = coloredLore.split("\n");
            meta.setLore(Arrays.asList(loreLines));
        }
        return this;
    }

    /**
     * Retrieves the lore of the ItemStack.
     *
     * @return The lore as a list of strings.
     */
    public List<String> getLore() {
        return meta.getLore();
    }

    /**
     * Adds flags to the ItemStack.
     *
     * @param flags The flags to add.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder flags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    /**
     * Removes flags from the ItemStack.
     *
     * @param flags The flags to remove.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder removeFlags(ItemFlag... flags) {
        meta.removeItemFlags(flags);
        return this;
    }

    /**
     * Retrieves the flags of the ItemStack.
     *
     * @return The set of flags.
     */
    public Set<ItemFlag> getFlags() {
        return meta.getItemFlags();
    }

    /**
     * Adds an enchantment to the ItemStack.
     *
     * @param enchantment The enchantment to add.
     * @param level       The level of the enchantment.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder enchantment(Enchantment enchantment, int level) {
        meta.addEnchant(enchantment, level, true);
        return this;
    }

    /**
     * Removes an enchantment from the ItemStack.
     *
     * @param enchantment The enchantment to remove.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        meta.removeEnchant(enchantment);
        return this;
    }

    /**
     * Retrieves the enchantments of the ItemStack.
     *
     * @return The map of enchantments.
     */
    public Map<Enchantment, Integer> getEnchantments() {
        return meta.getEnchants();
    }

    /**
     * Adds default flags to the ItemStack.
     *
     * @return The ItemBuilder instance.
     */
    public ItemBuilder defaultFlags() {
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DYE, ItemFlag.HIDE_POTION_EFFECTS);
        return this;
    }

    /**
     * Sets the unbreakable status of the ItemStack.
     *
     * @param unbreakable True if the ItemStack should be unbreakable, false otherwise.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder unbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Sets the material of the ItemStack.
     *
     * @param material The material to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder material(Material material) {
        item = new ItemStack(material);
        return this;
    }

    /**
     * Sets the meta of the ItemStack.
     *
     * @param itemMeta The ItemMeta to set.
     * @return The ItemBuilder instance.
     */
    public ItemBuilder setMeta(ItemMeta itemMeta) {
        meta = itemMeta;
        return this;
    }

    /**
     * Sets the ItemStack itself.
     *
     * @param stack The ItemStack to set.
     * @return The ItemBuilder instance.
     */
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
