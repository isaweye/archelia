package uk.mqchinee.featherlib.builders;

import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.loot.LootTable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import uk.mqchinee.featherlib.colors.Iridium;
import uk.mqchinee.featherlib.ext.MobBuilderInterface;
import uk.mqchinee.featherlib.utils.TextUtils;

public class MobBuilder implements MobBuilderInterface {

    private EntityType entityType;
    private Location location;
    private Mob mob;
    private final TextUtils t = new TextUtils();

    public MobBuilder(EntityType entityType, Location location) {
        this.entityType = entityType;
        this.location = location;
        this.mob = (Mob) location.getWorld().spawnEntity(location, entityType);
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
    public MobBuilder AI(boolean bool) {
        this.mob.setAI(bool);
        return this;
    }

    @Override
    public MobBuilder aware(boolean bool) {
        this.mob.setAware(bool);
        return this;
    }

    @Override
    public MobBuilder effect(PotionEffect effect) {
        this.mob.addPotionEffect(effect);
        return this;
    }

    @Override
    public MobBuilder removeEffect(PotionEffectType effect) {
        this.mob.removePotionEffect(effect);
        return this;
    }

    @Override
    public MobBuilder pickup(boolean bool) {
        this.mob.setCanPickupItems(bool);
        return this;
    }

    @Override
    public MobBuilder arrowsInBody(int integer) {
        this.mob.setArrowsInBody(integer);
        return this;
    }

    @Override
    public MobBuilder collidable(boolean bool) {
        this.mob.setCollidable(bool);
        return this;
    }

    @Override
    public MobBuilder health(double health) {
        this.mob.setHealth(health);
        return this;
    }

    @Override
    public MobBuilder target(LivingEntity target) {
        this.mob.setTarget(target);
        return this;
    }

    @Override
    public MobBuilder invisible(boolean bool) {
        this.mob.setInvisible(bool);
        return this;
    }

    @Override
    public MobBuilder loot(LootTable loot) {
        this.mob.setLootTable(loot);
        return this;
    }

    @Override
    public MobBuilder name(String name) {
        this.mob.setCustomName(t.colorize(name));
        return this;
    }

    @Override
    public MobBuilder invulnerable(boolean bool) {
        this.mob.setInvulnerable(bool);
        return this;
    }

    @Override
    public MobBuilder velocity(Vector velocity) {
        this.mob.setVelocity(velocity);
        return this;
    }

    @Override
    public MobBuilder gravity(boolean bool) {
        this.mob.setGravity(bool);
        return this;
    }

    @Override
    public MobBuilder visible(boolean visible) {
        this.mob.setCustomNameVisible(visible);
        return this;
    }

    @Override
    public MobBuilder silent(boolean bool) {
        this.mob.setSilent(bool);
        return this;
    }

    @Override
    public MobBuilder freeze(int ticks) {
        this.mob.setFireTicks(ticks);
        return this;
    }

    @Override
    public MobBuilder ignite(int ticks) {
        this.mob.setFireTicks(ticks);
        return this;
    }

    @Override
    public MobBuilder glow(boolean bool) {
        this.mob.setGlowing(bool);
        return this;
    }

    @Override
    public MobBuilder fire(boolean bool) {
        this.mob.setVisualFire(bool);
        return this;
    }

    @Override
    public MobBuilder removePassenger(Entity entity) {
        this.mob.removePassenger(entity);
        return this;
    }

    @Override
    public MobBuilder addPassenger(Entity entity) {
        this.mob.addPassenger(entity);
        return this;
    }

    @Override
    public MobBuilder age(Age age) {
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

    @Override
    public MobBuilder hex(String name) {
        this.mob.setCustomName(Iridium.process(name));
        return this;
    }

    @Override
    public Mob build() {
        return this.mob;
    }

}