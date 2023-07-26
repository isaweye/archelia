package uk.mqchinee.archelia.impl;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.loot.LootTable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import uk.mqchinee.archelia.builders.MobBuilder;

public interface MobBuilderInterface {

    /**
     * Gets the location of the mob.
     *
     * @return The location of the mob.
     */
    Location getLocation();

    /**
     * Gets the entity type of the mob.
     *
     * @return The entity type of the mob.
     */
    EntityType getEntityType();

    /**
     * Sets whether the AI of the mob is enabled or disabled.
     *
     * @param bool If true, the AI will be enabled. If false, it will be disabled.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder AI(boolean bool);

    /**
     * Sets whether the mob is aware of its surroundings.
     *
     * @param bool If true, the mob will be aware of its surroundings. If false, it will not be aware.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder aware(boolean bool);

    /**
     * Adds a potion effect to the mob.
     *
     * @param effect The potion effect to add to the mob.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder effect(PotionEffect effect);

    /**
     * Removes a specific potion effect from the mob.
     *
     * @param effect The potion effect type to remove from the mob.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder removeEffect(PotionEffectType effect);

    /**
     * Sets whether the mob can pick up items or not.
     *
     * @param bool If true, the mob will be able to pick up items. If false, it will not be able to.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder pickup(boolean bool);

    /**
     * Sets the number of arrows in the mob's body.
     *
     * @param integer The number of arrows to set in the mob's body.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder arrowsInBody(int integer);

    /**
     * Sets whether the mob is collidable or not.
     *
     * @param bool If true, the mob will be collidable. If false, it will not be collidable.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder collidable(boolean bool);

    /**
     * Sets the health of the mob.
     *
     * @param health The health value to set for the mob.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder health(double health);

    /**
     * Sets the target for the mob to attack.
     *
     * @param target The living entity that the mob will target.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder target(LivingEntity target);

    /**
     * Sets whether the mob is invisible or not.
     *
     * @param bool If true, the mob will be invisible. If false, it will be visible.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder invisible(boolean bool);

    /**
     * Sets the loot table for the mob.
     *
     * @param loot The loot table to set for the mob.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder loot(LootTable loot);

    /**
     * Sets the name of the mob.
     *
     * @param name The name to set for the mob.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder name(String name);

    /**
     * Sets whether the mob is invulnerable or not.
     *
     * @param bool If true, the mob will be invulnerable. If false, it will be vulnerable.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder invulnerable(boolean bool);

    /**
     * Sets the velocity of the mob.
     *
     * @param velocity The velocity vector to set for the mob.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder velocity(Vector velocity);

    /**
     * Sets whether gravity affects the mob or not.
     *
     * @param bool If true, gravity will affect the mob. If false, it will not be affected by gravity.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder gravity(boolean bool);

    /**
     * Sets whether the mob is visible or not.
     *
     * @param visible If true, the mob will be visible. If false, it will be invisible.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder visible(boolean visible);

    /**
     * Sets whether the mob is silent or not.
     *
     * @param bool If true, the mob will be silent. If false, it will make sounds.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder silent(boolean bool);

    /**
     * Freezes the mob for a specific number of ticks.
     *
     * @param ticks The number of ticks to freeze the mob.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder freeze(int ticks);

    /**
     * Ignites the mob for a specific number of ticks.
     *
     * @param ticks The number of ticks to ignite the mob.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder ignite(int ticks);

    /**
     * Sets whether the mob will have a glowing effect or not.
     *
     * @param bool If true, the mob will have a glowing effect. If false, it will not have the effect.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder glow(boolean bool);

    /**
     * Sets whether the mob is on fire or not.
     *
     * @param bool If true, the mob will be set on fire. If false, it will not be on fire.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder fire(boolean bool);

    /**
     * Removes a passenger entity from the mob.
     *
     * @param entity The entity to remove as a passenger from the mob.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder removePassenger(Entity entity);

    /**
     * Adds an entity as a passenger to the mob.
     *
     * @param entity The entity to add as a passenger to the mob.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder addPassenger(Entity entity);

    /**
     * Sets the age of the mob.
     *
     * @param age The age to set for the mob (ADULT or BABY).
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder age(Age age);

    /**
     * Sets the hex value for the mob's name.
     *
     * @param name The hex value for the mob's name.
     * @return The MobBuilderInterface instance for method chaining.
     */
    MobBuilder hex(String name);

    /**
     * Builds the mob with the specified configurations.
     *
     * @return The built mob.
     */
    Mob build();

    /**
     * Enumeration for mob age (ADULT or BABY).
     */
    public enum Age {
        ADULT, BABY
    }
}
