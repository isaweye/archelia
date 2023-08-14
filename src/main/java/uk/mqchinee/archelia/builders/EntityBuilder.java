package uk.mqchinee.archelia.builders;

import org.bukkit.Location;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import uk.mqchinee.archelia.impl.Builder;
import uk.mqchinee.archelia.utils.TextUtils;

/**
 * A builder class for creating and configuring entities in a Bukkit environment.
 *
 * @since 1.0
 */
public class EntityBuilder implements Builder<Entity> {

    private final EntityType entityType;
    private final Location location;
    private Entity entity;

    /**
     * Constructs an EntityBuilder with the specified entity type and location.
     *
     * @param entityType The EntityType of the entity to be created.
     * @param location   The location where the entity will be spawned.
     */
    public EntityBuilder(EntityType entityType, Location location) {
        this.entityType = entityType;
        this.location = location;
        this.entity = location.getWorld().spawnEntity(location, entityType);
    }

    /**
     * Retrieves the location where the entity will be spawned.
     *
     * @return The location of the entity.
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Retrieves the EntityType of the entity being built.
     *
     * @return The EntityType of the entity.
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * Sets a custom name for the entity.
     *
     * @param name The custom name to set.
     * @return The EntityBuilder instance.
     */
    public EntityBuilder name(String name) {
        this.entity.setCustomName(TextUtils.colorize(name));
        return this;
    }

    /**
     * Sets the invulnerability status of the entity.
     *
     * @param bool True if the entity should be invulnerable, false otherwise.
     * @return The EntityBuilder instance.
     */
    public EntityBuilder invulnerable(boolean bool) {
        this.entity.setInvulnerable(bool);
        return this;
    }

    /**
     * Sets the velocity of the entity.
     *
     * @param velocity The velocity vector to set.
     * @return The EntityBuilder instance.
     */
    public EntityBuilder velocity(Vector velocity) {
        this.entity.setVelocity(velocity);
        return this;
    }

    /**
     * Sets whether gravity affects the entity.
     *
     * @param bool True if gravity should affect the entity, false otherwise.
     * @return The EntityBuilder instance.
     */
    public EntityBuilder gravity(boolean bool) {
        this.entity.setGravity(bool);
        return this;
    }

    /**
     * Sets the visibility of the entity's custom name.
     *
     * @param visible True if the custom name should be visible, false otherwise.
     * @return The EntityBuilder instance.
     */
    public EntityBuilder visible(boolean visible) {
        this.entity.setCustomNameVisible(visible);
        return this;
    }

    /**
     * Sets whether the entity is silent.
     *
     * @param bool True if the entity should be silent, false otherwise.
     * @return The EntityBuilder instance.
     */
    public EntityBuilder silent(boolean bool) {
        this.entity.setSilent(bool);
        return this;
    }

    /**
     * Sets the fire ticks for the entity to simulate freezing.
     *
     * @param ticks The number of fire ticks to set.
     * @return The EntityBuilder instance.
     */
    public EntityBuilder freeze(int ticks) {
        this.entity.setFireTicks(ticks);
        return this;
    }

    /**
     * Sets the fire ticks for the entity to ignite it.
     *
     * @param ticks The number of fire ticks to set.
     * @return The EntityBuilder instance.
     */
    public EntityBuilder ignite(int ticks) {
        this.entity.setFireTicks(ticks);
        return this;
    }

    /**
     * Sets whether the entity appears to be glowing.
     *
     * @param bool True if the entity should appear glowing, false otherwise.
     * @return The EntityBuilder instance.
     */
    public EntityBuilder glow(boolean bool) {
        this.entity.setGlowing(bool);
        return this;
    }

    /**
     * Sets whether the entity is visually on fire.
     *
     * @param bool True if the entity should appear visually on fire, false otherwise.
     * @return The EntityBuilder instance.
     */
    public EntityBuilder fire(boolean bool) {
        this.entity.setVisualFire(bool);
        return this;
    }

    /**
     * Removes a passenger from the entity.
     *
     * @param entity The entity to be removed as a passenger.
     * @return The EntityBuilder instance.
     */
    public EntityBuilder removePassenger(Entity entity) {
        this.entity.removePassenger(entity);
        return this;
    }

    /**
     * Adds a passenger to the entity.
     *
     * @param entity The entity to be added as a passenger.
     * @return The EntityBuilder instance.
     */
    public EntityBuilder addPassenger(Entity entity) {
        this.entity.addPassenger(entity);
        return this;
    }

    /**
     * Sets the age of an Ageable entity.
     *
     * @param age The age to set (BABY or ADULT).
     * @return The EntityBuilder instance.
     */
    public EntityBuilder age(Age age) {
        if (this.entity instanceof Ageable ageable) {
            switch (age) {
                case BABY -> {
                    ageable.setBaby();
                    this.entity = ageable;
                }
                case ADULT -> {
                    ageable.setAdult();
                    this.entity = ageable;
                }
            }
        }
        return this;
    }

    @Override
    public Entity build() {
        return this.entity;
    }

    /**
     * Enumeration representing the possible age states for an Ageable entity.
     */
    public enum Age {
        BABY, ADULT
    }
}
