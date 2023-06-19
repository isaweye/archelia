package uk.mqchinee.featherapi.raycast.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import uk.mqchinee.featherapi.raycast.Projectile;

public class ProjectilePenetrateBlockEvent extends ProjectileHitBlockEvent {

    public ProjectilePenetrateBlockEvent(LivingEntity who, Location start, Location end, Block what, Projectile projectile) {
        super(who, start, end, what, projectile);
    }
}