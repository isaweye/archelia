package uk.mqchinee.featherapi.raycast.events;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import uk.mqchinee.featherapi.raycast.Projectile;

public class ProjectileHitEntityEvent extends ProjectileEvent {
    private Entity entity;

    public ProjectileHitEntityEvent(LivingEntity who, Location start, Location end, Entity what, Projectile projectile) {
        super(who, start, end, projectile);
        this.entity = what;
    }

    public Entity getEntity() {
        if (!this.canceled)
            return this.entity;
        return null;
    }
}
