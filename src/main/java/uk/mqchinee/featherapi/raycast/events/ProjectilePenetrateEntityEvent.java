package uk.mqchinee.featherapi.raycast.events;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import uk.mqchinee.featherapi.raycast.Projectile;

public class ProjectilePenetrateEntityEvent extends ProjectileHitEntityEvent {

    public ProjectilePenetrateEntityEvent(LivingEntity who, Location start, Location end, Entity what, Projectile projectile) {
        super(who, start, end, what, projectile);
    }
}
