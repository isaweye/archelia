package uk.mqchinee.archelia.impl;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

public interface EntityBuilderInterface {

    /**
     * Gets the location where the entity will be spawned.
     *
     * @return The location for the entity.
     */
    Location getLocation();

    /**
     * Gets the EntityType of the entity to be spawned.
     *
     * @return The EntityType of the entity.
     */
    EntityType getEntityType();

    /**
     * Sets the name of the entity.
     *
     * @param name The name to set for the entity.
     * @return The EntityBuilderInterface instance for method chaining.
     */
    EntityBuilderInterface name(String name);

    /**
     * Sets whether the entity will be invulnerable or not.
     *
     * @param bool If true, the entity will be invulnerable. If false, it will not be invulnerable.
     * @return The EntityBuilderInterface instance for method chaining.
     */
    EntityBuilderInterface invulnerable(boolean bool);

    /**
     * Sets the velocity of the entity.
     *
     * @param velocity The velocity vector to set for the entity.
     * @return The EntityBuilderInterface instance for method chaining.
     */
    EntityBuilderInterface velocity(Vector velocity);

    /**
     * Sets whether gravity affects the entity or not.
     *
     * @param bool If true, gravity will affect the entity. If false, it will not be affected by gravity.
     * @return The EntityBuilderInterface instance for method chaining.
     */
    EntityBuilderInterface gravity(boolean bool);

    /**
     * Sets whether the entity will be visible or not.
     *
     * @param visible If true, the entity will be visible. If false, it will be invisible.
     * @return The EntityBuilderInterface instance for method chaining.
     */
    EntityBuilderInterface visible(boolean visible);

    /**
     * Sets whether the entity will make any sounds or not.
     *
     * @param bool If true, the entity will be silent and not make sounds. If false, it will make sounds as usual.
     * @return The EntityBuilderInterface instance for method chaining.
     */
    EntityBuilderInterface silent(boolean bool);

    /**
     * Freezes the entity for a specific number of ticks.
     *
     * @param ticks The number of ticks to freeze the entity for.
     * @return The EntityBuilderInterface instance for method chaining.
     */
    EntityBuilderInterface freeze(int ticks);

    /**
     * Ignites the entity for a specific number of ticks.
     *
     * @param ticks The number of ticks to ignite the entity for.
     * @return The EntityBuilderInterface instance for method chaining.
     */
    EntityBuilderInterface ignite(int ticks);

    /**
     * Sets whether the entity will glow or not.
     *
     * @param bool If true, the entity will glow. If false, it will not glow.
     * @return The EntityBuilderInterface instance for method chaining.
     */
    EntityBuilderInterface glow(boolean bool);

    /**
     * Sets whether the entity will be on fire or not.
     *
     * @param bool If true, the entity will be on fire. If false, it will not be on fire.
     * @return The EntityBuilderInterface instance for method chaining.
     */
    EntityBuilderInterface fire(boolean bool);

    /**
     * Removes a passenger entity from this entity.
     *
     * @param entity The passenger entity to be removed.
     * @return The EntityBuilderInterface instance for method chaining.
     */
    EntityBuilderInterface removePassenger(Entity entity);

    /**
     * Adds a passenger entity to this entity.
     *
     * @param entity The passenger entity to be added.
     * @return The EntityBuilderInterface instance for method chaining.
     */
    EntityBuilderInterface addPassenger(Entity entity);

    /**
     * Sets the age of the entity. Can be ADULT or BABY.
     *
     * @param age The age to set for the entity.
     * @return The EntityBuilderInterface instance for method chaining.
     */
    EntityBuilderInterface age(Age age);

    /**
     * Builds and spawns the entity with the specified configurations.
     *
     * @return The spawned entity.
     */
    Entity build();

    /**
     * Enumeration representing the age of an entity (ADULT or BABY).
     */
    public enum Age {
        ADULT, BABY
    }
}
