package uk.mqchinee.featherapi.raycast.events;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import uk.mqchinee.featherapi.raycast.Projectile;

public class ProjectileHitBlockEvent extends ProjectileEvent {
    private Block block;

    public ProjectileHitBlockEvent(LivingEntity who, Location start, Location end, Block what, Projectile projectile) {
        super(who, start, end, projectile);
        this.block = what;
    }

    public Block getBlock() {
        if (!this.canceled)
            return this.block;
        return null;
    }
}
