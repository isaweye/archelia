package uk.mqchinee.archelia.builders;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

public class Armor {

    @Getter @Setter private ItemStack helmet;
    @Getter @Setter private ItemStack chestplate;
    @Getter @Setter private ItemStack leggings;
    @Getter @Setter private ItemStack boots;

    public Armor(ItemStack helmet, ItemStack chestplate, ItemStack leggings, ItemStack boots) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    public Armor() {}
}

