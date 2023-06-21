package uk.mqchinee.featherlib.impl;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import uk.mqchinee.featherlib.gui.core.ItemBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ItemBuilderInterface {
    ItemBuilder amount(int integer);

    int getAmount();

    ItemBuilder name(String string);

    String getName();

    ItemBuilder lore(String string);

    List<String> getLore();

    ItemBuilder flags(ItemFlag... flag);

    ItemBuilder removeFlags(ItemFlag... flag);

    Set<ItemFlag> getFlags();

    ItemBuilder enchantment(Enchantment enchantment, int level);

    ItemBuilder removeEnchantment(Enchantment enchantment);

    Map<Enchantment, Integer> getEnchantments();

    ItemBuilder defaultFlags();

    ItemBuilder unbreakable(boolean bool);

    ItemBuilder material(Material material);

    ItemBuilder setMeta(ItemMeta m);

    ItemBuilder itemstack(ItemStack stack);

    ItemStack build();
}
