package uk.mqchinee.featherlib.builders;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.loot.LootTable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class EntityBuilder {

    private EntityType entityType;
    private Location location;
    private Entity entity;
    private Mob mob;

    public enum Age {
        ADULT, BABY
    }

    public EntityBuilder(EntityType entityType, Location location) {
        this.entityType = entityType;
        this.location = location;
        this.entity = location.getWorld().spawnEntity(location, entityType);
        this.mob = (Mob) entity;
    }

    public Location getLocation() {
        return location;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public EntityBuilder AI(boolean bool) {
        this.mob.setAI(bool);
        return this;
    }

    public EntityBuilder aware(boolean bool) {
        this.mob.setAware(bool);
        return this;
    }

    public EntityBuilder effect(PotionEffect effect) {
        this.mob.addPotionEffect(effect);
        return this;
    }

    public EntityBuilder removeEffect(PotionEffectType effect) {
        this.mob.removePotionEffect(effect);
        return this;
    }

    public EntityBuilder pickup(boolean bool) {
        this.mob.setCanPickupItems(bool);
        return this;
    }

    public EntityBuilder arrowsInBody(int integer) {
        this.mob.setArrowsInBody(integer);
        return this;
    }

    public EntityBuilder collidable(boolean bool) {
        this.mob.setCollidable(bool);
        return this;
    }

    public EntityBuilder health(double health) {
        this.mob.setHealth(health);
        return this;
    }

    public EntityBuilder target(LivingEntity target) {
        this.mob.setTarget(target);
        return this;
    }

    public EntityBuilder invisible(boolean bool) {
        this.mob.setInvisible(bool);
        return this;
    }

    public EntityBuilder loot(LootTable loot) {
        this.mob.setLootTable(loot);
        return this;
    }

    public EntityBuilder name(String name) {
        this.entity.setCustomName(name);
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
        if (this.entity instanceof Ageable) {
            Ageable ageable = (Ageable) this.entity;
            switch (age) {
                case BABY:
                    (ageable).setBaby();
                case ADULT:
                    (ageable).setAdult();
            }
            this.entity = ageable;
        }
        return this;
    }

    public Mob mob() {
        return this.mob;
    }

    public Entity entity() {
        return this.entity;
    }

}
