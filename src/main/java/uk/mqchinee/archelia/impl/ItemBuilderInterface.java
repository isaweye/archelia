package uk.mqchinee.archelia.impl;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.mqchinee.archelia.builders.ItemBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ItemBuilderInterface {

    /**
     * Sets the amount of the item.
     *
     * @param integer The amount to set for the item.
     * @return The ItemBuilderInterface instance for method chaining.
     */
    ItemBuilder amount(int integer);

    /**
     * Gets the amount of the item.
     *
     * @return The amount of the item.
     */
    int getAmount();

    /**
     * Sets the name of the item.
     *
     * @param string The name to set for the item.
     * @return The ItemBuilderInterface instance for method chaining.
     */
    ItemBuilder name(String string);

    /**
     * Gets the name of the item.
     *
     * @return The name of the item.
     */
    String getName();

    /**
     * Adds a line of lore to the item.
     *
     * @param string The lore line to add to the item.
     * @return The ItemBuilderInterface instance for method chaining.
     */
    ItemBuilder lore(String string);

    /**
     * Gets the lore of the item.
     *
     * @return The lore of the item.
     */
    List<String> getLore();

    /**
     * Sets specific flags for the item.
     *
     * @param flag The flags to set for the item.
     * @return The ItemBuilderInterface instance for method chaining.
     */
    ItemBuilder flags(ItemFlag... flag);

    /**
     * Removes specific flags from the item.
     *
     * @param flag The flags to remove from the item.
     * @return The ItemBuilderInterface instance for method chaining.
     */
    ItemBuilder removeFlags(ItemFlag... flag);

    /**
     * Gets the flags set on the item.
     *
     * @return The set of flags on the item.
     */
    Set<ItemFlag> getFlags();

    /**
     * Adds an enchantment with a specific level to the item.
     *
     * @param enchantment The enchantment to add to the item.
     * @param level       The level of the enchantment.
     * @return The ItemBuilderInterface instance for method chaining.
     */
    ItemBuilder enchantment(Enchantment enchantment, int level);

    /**
     * Removes a specific enchantment from the item.
     *
     * @param enchantment The enchantment to remove from the item.
     * @return The ItemBuilderInterface instance for method chaining.
     */
    ItemBuilder removeEnchantment(Enchantment enchantment);

    /**
     * Gets the enchantments applied to the item.
     *
     * @return A map containing the enchantments and their levels.
     */
    Map<Enchantment, Integer> getEnchantments();

    /**
     * Sets default flags for the item, making it show enchantments, etc.
     *
     * @return The ItemBuilderInterface instance for method chaining.
     */
    ItemBuilder defaultFlags();

    /**
     * Sets whether the item will be unbreakable or not.
     *
     * @param bool If true, the item will be unbreakable. If false, it will be breakable.
     * @return The ItemBuilderInterface instance for method chaining.
     */
    ItemBuilder unbreakable(boolean bool);

    /**
     * Sets the material of the item.
     *
     * @param material The material to set for the item.
     * @return The ItemBuilderInterface instance for method chaining.
     */
    ItemBuilder material(Material material);

    /**
     * Sets the metadata for the item.
     *
     * @param m The ItemMeta to set for the item.
     * @return The ItemBuilderInterface instance for method chaining.
     */
    ItemBuilder setMeta(ItemMeta m);

    /**
     * Sets the ItemStack to be built.
     *
     * @param stack The ItemStack to set.
     * @return The ItemBuilderInterface instance for method chaining.
     */
    ItemBuilder itemstack(ItemStack stack);

    /**
     * Builds the ItemStack with the specified configurations.
     *
     * @return The built ItemStack.
     */
    ItemStack build();
}
