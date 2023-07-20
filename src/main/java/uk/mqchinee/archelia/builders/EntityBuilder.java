package uk.mqchinee.archelia.builders;

import org.bukkit.Location;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import uk.mqchinee.archelia.impl.EntityBuilderInterface;
import uk.mqchinee.archelia.utils.TextUtils;

public class EntityBuilder implements EntityBuilderInterface {

    private final EntityType entityType;
    private final Location location;
    private Entity entity;

    public EntityBuilder(EntityType entityType, Location location) {
        this.entityType = entityType;
        this.location = location;
        this.entity = location.getWorld().spawnEntity(location, entityType);
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public EntityType getEntityType() {
        return entityType;
    }

    @Override
    public EntityBuilder name(String name) {
        this.entity.setCustomName(TextUtils.colorize(name));
        return this;
    }

    @Override
    public EntityBuilder invulnerable(boolean bool) {
        this.entity.setInvulnerable(bool);
        return this;
    }

    @Override
    public EntityBuilder velocity(Vector velocity) {
        this.entity.setVelocity(velocity);
        return this;
    }

    @Override
    public EntityBuilder gravity(boolean bool) {
        this.entity.setGravity(bool);
        return this;
    }

    @Override
    public EntityBuilder visible(boolean visible) {
        this.entity.setCustomNameVisible(visible);
        return this;
    }

    @Override
    public EntityBuilder silent(boolean bool) {
        this.entity.setSilent(bool);
        return this;
    }

    @Override
    public EntityBuilder freeze(int ticks) {
        this.entity.setFireTicks(ticks);
        return this;
    }

    @Override
    public EntityBuilder ignite(int ticks) {
        this.entity.setFireTicks(ticks);
        return this;
    }

    @Override
    public EntityBuilder glow(boolean bool) {
        this.entity.setGlowing(bool);
        return this;
    }

    @Override
    public EntityBuilder fire(boolean bool) {
        this.entity.setVisualFire(bool);
        return this;
    }

    @Override
    public EntityBuilder removePassenger(Entity entity) {
        this.entity.removePassenger(entity);
        return this;
    }

    @Override
    public EntityBuilder addPassenger(Entity entity) {
        this.entity.addPassenger(entity);
        return this;
    }

    @Override
    public EntityBuilder age(Age age) {
        if (this.entity instanceof Ageable ageable) {
            switch (age) {
                case BABY:
                    (ageable).setBaby();
                    this.entity = ageable;
                case ADULT:
                    (ageable).setAdult();
                    this.entity = ageable;
            }
        }
        return this;
    }

    @Override
    public Entity build() {
        return this.entity;
    }

}
