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

    public Location getLocation() {
        return location;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public MobBuilder AI(boolean bool) {
        this.mob.setAI(bool);
        return this;
    }

    public MobBuilder aware(boolean bool) {
        this.mob.setAware(bool);
        return this;
    }

    public MobBuilder effect(PotionEffect effect) {
        this.mob.addPotionEffect(effect);
        return this;
    }

    public MobBuilder removeEffect(PotionEffectType effect) {
        this.mob.removePotionEffect(effect);
        return this;
    }

    public MobBuilder pickup(boolean bool) {
        this.mob.setCanPickupItems(bool);
        return this;
    }

    public MobBuilder arrowsInBody(int integer) {
        this.mob.setArrowsInBody(integer);
        return this;
    }

    public MobBuilder collidable(boolean bool) {
        this.mob.setCollidable(bool);
        return this;
    }

    public MobBuilder health(double health) {
        this.mob.setHealth(health);
        return this;
    }

    public MobBuilder target(LivingEntity target) {
        this.mob.setTarget(target);
        return this;
    }

    public MobBuilder invisible(boolean bool) {
        this.mob.setInvisible(bool);
        return this;
    }

    public MobBuilder loot(LootTable loot) {
        this.mob.setLootTable(loot);
        return this;
    }

    public MobBuilder name(String name) {
        this.mob.setCustomName(TextUtils.colorize(name));
        return this;
    }

    public MobBuilder invulnerable(boolean bool) {
        this.mob.setInvulnerable(bool);
        return this;
    }

    public MobBuilder velocity(Vector velocity) {
        this.mob.setVelocity(velocity);
        return this;
    }

    public MobBuilder gravity(boolean bool) {
        this.mob.setGravity(bool);
        return this;
    }

    public MobBuilder visible(boolean visible) {
        this.mob.setCustomNameVisible(visible);
        return this;
    }

    public MobBuilder silent(boolean bool) {
        this.mob.setSilent(bool);
        return this;
    }

    public MobBuilder freeze(int ticks) {
        this.mob.setFireTicks(ticks);
        return this;
    }

    public MobBuilder ignite(int ticks) {
        this.mob.setFireTicks(ticks);
        return this;
    }

    public MobBuilder glow(boolean bool) {
        this.mob.setGlowing(bool);
        return this;
    }

    public MobBuilder fire(boolean bool) {
        this.mob.setVisualFire(bool);
        return this;
    }

    public MobBuilder removePassenger(Entity entity) {
        this.mob.removePassenger(entity);
        return this;
    }

    public MobBuilder addPassenger(Entity entity) {
        this.mob.addPassenger(entity);
        return this;
    }

    public MobBuilder age(EntityBuilder.Age age) {
        if (this.mob instanceof Ageable) {
            Ageable a = (Ageable) build();
            switch (age) {
                case BABY:
                    a.setBaby();
                case ADULT:
                    a.setAdult();
            }
        }
        return this;
    }

    public MobBuilder hex(String name) {
        this.mob.setCustomName(Iridium.process(name));
        return this;
    }

    @Override
    public Mob build() {
        return this.mob;
    }

}