package uk.mqchinee.lanterncore.impl;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.loot.LootTable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import uk.mqchinee.lanterncore.builders.MobBuilder;

public interface MobBuilderInterface {
    Location getLocation();

    EntityType getEntityType();

    MobBuilder AI(boolean bool);

    MobBuilder aware(boolean bool);

    MobBuilder effect(PotionEffect effect);

    MobBuilder removeEffect(PotionEffectType effect);

    MobBuilder pickup(boolean bool);

    MobBuilder arrowsInBody(int integer);

    MobBuilder collidable(boolean bool);

    MobBuilder health(double health);

    MobBuilder target(LivingEntity target);

    MobBuilder invisible(boolean bool);

    MobBuilder loot(LootTable loot);

    MobBuilder name(String name);

    MobBuilder invulnerable(boolean bool);

    MobBuilder velocity(Vector velocity);

    MobBuilder gravity(boolean bool);

    MobBuilder visible(boolean visible);

    MobBuilder silent(boolean bool);

    MobBuilder freeze(int ticks);

    MobBuilder ignite(int ticks);

    MobBuilder glow(boolean bool);

    MobBuilder fire(boolean bool);

    MobBuilder removePassenger(Entity entity);

    MobBuilder addPassenger(Entity entity);

    MobBuilder age(Age age);

    MobBuilder hex(String name);

    Mob build();

    public enum Age {
        ADULT, BABY
    }
}
