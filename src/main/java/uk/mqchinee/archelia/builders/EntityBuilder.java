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


    public Location getLocation() {
        return location;
    }


    public EntityType getEntityType() {
        return entityType;
    }


    public EntityBuilder name(String name) {
        this.entity.setCustomName(TextUtils.colorize(name));
        return this;
    }


    public EntityBuilder invulnerable(boolean bool) {
        this.entity.setInvulnerable(bool);
        return this;
    }


    public EntityBuilder velocity(Vector velocity) {
        this.entity.setVelocity(velocity);
        return this;
    }


    public EntityBuilder gravity(boolean bool) {
        this.entity.setGravity(bool);
        return this;
    }


    public EntityBuilder visible(boolean visible) {
        this.entity.setCustomNameVisible(visible);
        return this;
    }


    public EntityBuilder silent(boolean bool) {
        this.entity.setSilent(bool);
        return this;
    }


    public EntityBuilder freeze(int ticks) {
        this.entity.setFireTicks(ticks);
        return this;
    }


    public EntityBuilder ignite(int ticks) {
        this.entity.setFireTicks(ticks);
        return this;
    }


    public EntityBuilder glow(boolean bool) {
        this.entity.setGlowing(bool);
        return this;
    }


    public EntityBuilder fire(boolean bool) {
        this.entity.setVisualFire(bool);
        return this;
    }


    public EntityBuilder removePassenger(Entity entity) {
        this.entity.removePassenger(entity);
        return this;
    }


    public EntityBuilder addPassenger(Entity entity) {
        this.entity.addPassenger(entity);
        return this;
    }


    public EntityBuilder age(Age age) {
        if (this.entity instanceof Ageable ageable) {
            switch (age) {
                case BABY -> {
                    (ageable).setBaby();
                    this.entity = ageable;
                }
                case ADULT -> {
                    (ageable).setAdult();
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

    public enum Age {
        BABY, ADULT
    }

}
