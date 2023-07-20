package uk.mqchinee.archelia.impl;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;

public interface EntityBuilderInterface {
    Location getLocation();

    EntityType getEntityType();

    EntityBuilderInterface name(String name);

    EntityBuilderInterface invulnerable(boolean bool);

    EntityBuilderInterface velocity(Vector velocity);

    EntityBuilderInterface gravity(boolean bool);

    EntityBuilderInterface visible(boolean visible);

    EntityBuilderInterface silent(boolean bool);

    EntityBuilderInterface freeze(int ticks);

    EntityBuilderInterface ignite(int ticks);

    EntityBuilderInterface glow(boolean bool);

    EntityBuilderInterface fire(boolean bool);

    EntityBuilderInterface removePassenger(Entity entity);

    EntityBuilderInterface addPassenger(Entity entity);

    EntityBuilderInterface age(Age age);

    Entity build();

    public enum Age {
        ADULT, BABY
    }
}
