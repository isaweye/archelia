package uk.mqchinee.archelia.builders;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

/**
 * Represents a set of armor items.
 */
public class Armor {

    @Getter @Setter private ItemStack helmet;
    @Getter @Setter private ItemStack chestplate;
    @Getter @Setter private ItemStack leggings;
    @Getter @Setter private ItemStack boots;

    /**
     * Constructs an Armor instance with the specified armor pieces.
     *
     * @param helmet     The helmet ItemStack.
     * @param chestplate The chestplate ItemStack.
     * @param leggings   The leggings ItemStack.
     * @param boots      The boots ItemStack.
     */
    public Armor(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    /**
     * Constructs an empty Armor instance.
     */
    public Armor() {
    }
}
