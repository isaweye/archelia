package uk.mqchinee.archelia.builders;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.loot.LootTable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import uk.mqchinee.archelia.colors.Iridium;
import uk.mqchinee.archelia.impl.Builder;
import uk.mqchinee.archelia.utils.TextUtils;

/**
 * A builder class for creating and customizing mobs (entities) in a Bukkit environment.
 *
 * @since 1.0
 */
public class MobBuilder implements Builder<Mob> {

    private final EntityType entityType;
    private final Location location;
    private final Mob mob;

    /**
     * Constructs a MobBuilder with the specified entity type and location.
     *
     * @param entityType The EntityType of the mob to be created.
     * @param location   The location where the mob will be spawned.
     */
    public MobBuilder(EntityType entityType, Location location) {
        this.entityType = entityType;
        this.location = location;
        this.mob = (Mob) location.getWorld().spawnEntity(location, entityType);
    }

    /**
     * Retrieves the location where the mob will be spawned.
     *
     * @return The location of the mob.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Retrieves the EntityType of the mob being built.
     *
     * @return The EntityType of the mob.
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * Sets the AI status of the mob.
     *
     * @param bool True if the mob's AI should be enabled, false otherwise.
     * @return The MobBuilder instance.
     */
    public MobBuilder AI(boolean bool) {
        this.mob.setAI(bool);
        return this;
    }

    /**
     * Sets the aware status of the mob.
     *
     * @param bool True if the mob should be aware, false otherwise.
     * @return The MobBuilder instance.
     */
    public MobBuilder aware(boolean bool) {
        this.mob.setAware(bool);
        return this;
    }

    /**
     * Adds a potion effect to the mob.
     *
     * @param effect The PotionEffect to add.
     * @return The MobBuilder instance.
     */
    public MobBuilder effect(PotionEffect effect) {
        this.mob.addPotionEffect(effect);
        return this;
    }

    /**
     * Removes a potion effect from the mob.
     *
     * @param effect The PotionEffectType to remove.
     * @return The MobBuilder instance.
     */
    public MobBuilder removeEffect(PotionEffectType effect) {
        this.mob.removePotionEffect(effect);
        return this;
    }

    /**
     * Sets whether the mob can pick up items.
     *
     * @param bool True if the mob can pick up items, false otherwise.
     * @return The MobBuilder instance.
     */
    public MobBuilder pickup(boolean bool) {
        this.mob.setCanPickupItems(bool);
        return this;
    }

    /**
     * Sets the number of arrows in the mob's body.
     *
     * @param amount The number of arrows.
     * @return The MobBuilder instance.
     */
    public MobBuilder arrowsInBody(int amount) {
        this.mob.setArrowsInBody(amount);
        return this;
    }

    /**
     * Sets the collidable status of the mob.
     *
     * @param bool True if the mob should be collidable, false otherwise.
     * @return The MobBuilder instance.
     */
    public MobBuilder collidable(boolean bool) {
        this.mob.setCollidable(bool);
        return this;
    }

    /**
     * Sets the health of the mob.
     *
     * @param health The health to set.
     * @return The MobBuilder instance.
     */
    public MobBuilder health(double health) {
        this.mob.setHealth(health);
        return this;
    }

    /**
     * Sets the target of the mob.
     *
     * @param target The LivingEntity to set as the target.
     * @return The MobBuilder instance.
     */
    public MobBuilder target(LivingEntity target) {
        this.mob.setTarget(target);
        return this;
    }

    /**
     * Sets the invisibility status of the mob.
     *
     * @param bool True if the mob should be invisible, false otherwise.
     * @return The MobBuilder instance.
     */
    public MobBuilder invisible(boolean bool) {
        this.mob.setInvisible(bool);
        return this;
    }

    /**
     * Sets the loot table of the mob.
     *
     * @param loot The LootTable to set.
     * @return The MobBuilder instance.
     */
    public MobBuilder loot(LootTable loot) {
        this.mob.setLootTable(loot);
        return this;
    }

    /**
     * Sets the custom name of the mob.
     *
     * @param name The custom name to set.
     * @return The MobBuilder instance.
     */
    public MobBuilder name(String name) {
        this.mob.setCustomName(TextUtils.colorize(name));
        return this;
    }

    /**
     * Sets the invulnerability status of the mob.
     *
     * @param bool True if the mob should be invulnerable, false otherwise.
     * @return The MobBuilder instance.
     */
    public MobBuilder invulnerable(boolean bool) {
        this.mob.setInvulnerable(bool);
        return this;
    }

    /**
     * Sets the velocity of the mob.
     *
     * @param velocity The velocity vector to set.
     * @return The MobBuilder instance.
     */
    public MobBuilder velocity(Vector velocity) {
        this.mob.setVelocity(velocity);
        return this;
    }

    /**
     * Sets whether gravity affects the mob.
     *
     * @param bool True if gravity should affect the mob, false otherwise.
     * @return The MobBuilder instance.
     */
    public MobBuilder gravity(boolean bool) {
        this.mob.setGravity(bool);
        return this;
    }

    /**
     * Sets the visibility of the mob's custom name.
     *
     * @param visible True if the custom name should be visible, false otherwise.
     * @return The MobBuilder instance.
     */
    public MobBuilder visible(boolean visible) {
        this.mob.setCustomNameVisible(visible);
        return this;
    }

    /**
     * Sets whether the mob is silent.
     *
     * @param bool True if the mob should be silent, false otherwise.
     * @return The MobBuilder instance.
     */
    public MobBuilder silent(boolean bool) {
        this.mob.setSilent(bool);
        return this;
    }

    /**
     * Sets the fire ticks for the mob to simulate freezing.
     *
     * @param ticks The number of fire ticks to set.
     * @return The MobBuilder instance.
     */
    public MobBuilder freeze(int ticks) {
        this.mob.setFireTicks(ticks);
        return this;
    }

    /**
     * Sets the fire ticks for the mob to simulate ignition.
     *
     * @param ticks The number of fire ticks to set.
     * @return The MobBuilder instance.
     */
    public MobBuilder ignite(int ticks) {
        this.mob.setFireTicks(ticks);
        return this;
    }

    /**
     * Sets the glowing status of the mob.
     *
     * @param bool True if the mob should appear glowing, false otherwise.
     * @return The MobBuilder instance.
     */
    public MobBuilder glow(boolean bool) {
        this.mob.setGlowing(bool);
        return this;
    }

    /**
     * Sets whether the mob is on fire visually.
     *
     * @param bool True if the mob should appear on fire, false otherwise.
     * @return The MobBuilder instance.
     */
    public MobBuilder fire(boolean bool) {
        this.mob.setVisualFire(bool);
        return this;
    }

    /**
     * Removes a passenger from the mob.
     *
     * @param entity The Entity passenger to remove.
     * @return The MobBuilder instance.
     */
    public MobBuilder removePassenger(Entity entity) {
        this.mob.removePassenger(entity);
        return this;
    }

    /**
     * Adds a passenger to the mob.
     *
     * @param entity The Entity passenger to add.
     * @return The MobBuilder instance.
     */
    public MobBuilder addPassenger(Entity entity) {
        this.mob.addPassenger(entity);
        return this;
    }

    /**
     * Sets the age of the mob if it's Ageable.
     *
     * @param age The age to set (BABY or ADULT).
     * @return The MobBuilder instance.
     */
    public MobBuilder age(EntityBuilder.Age age) {
        if (this.mob instanceof Ageable) {
            Ageable ageable = (Ageable) mob;
            switch (age) {
                case BABY:
                    ageable.setBaby();
                    break;
                case ADULT:
                    ageable.setAdult();
                    break;
            }
        }
        return this;
    }

    /**
     * Sets the custom name of the mob using Iridium color codes.
     *
     * @param name The custom name to set.
     * @return The MobBuilder instance.
     */
    public MobBuilder hex(String name) {
        this.mob.setCustomName(Iridium.process(name));
        return this;
    }

    @Override
    public Mob build() {
        return this.mob;
    }
}
