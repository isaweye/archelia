package uk.mqchinee.featherapi.raycast.events;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import uk.mqchinee.featherapi.raycast.ParticleColor;
import uk.mqchinee.featherapi.raycast.Projectile;

public class ProjectileEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    protected boolean canceled = false;

    protected Projectile particle_projectile;

    private LivingEntity source;

    private Location start_location;

    private Location end_location;

    public ProjectileEvent(LivingEntity who, Location start, Location end, Projectile projectile) {
        this.source = who;
        this.start_location = start;
        this.end_location = end;
        this.particle_projectile = projectile;
    }

    public LivingEntity getSourceEntity() {
        if (!this.canceled)
            return this.source;
        return null;
    }

    public Location getStart() {
        if (!this.canceled)
            return this.start_location;
        return null;
    }

    public Location getEnd() {
        if (!this.canceled)
            return this.end_location;
        return null;
    }

    public ParticleColor getColor() {
        if (!this.canceled)
            return this.particle_projectile.getColor();
        return null;
    }

    public void setColor(ParticleColor color) {
        if (!this.canceled)
            this.particle_projectile.setColor(color);
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public boolean isCancelled() {
        return this.canceled;
    }

    public void setCancelled(boolean cancel) {
        this.canceled = cancel;
    }
}