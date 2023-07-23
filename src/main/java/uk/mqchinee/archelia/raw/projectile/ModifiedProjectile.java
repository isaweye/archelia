package uk.mqchinee.archelia.raw.projectile;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Abstract class representing a modified projectile.
 * This class provides methods to handle the behavior of the projectile after launch.
 */
public abstract class ModifiedProjectile {

    @Getter
    @Setter
    private Projectile projectile;
    @Getter private final LivingEntity shooter;
    @Getter private final JavaPlugin plugin;

    /**
     * Constructor for the ModifiedProjectile class.
     *
     * @param plugin The JavaPlugin instance associated with this modified projectile.
     * @param projectile The projectile entity to be modified.
     * @param shooter The living entity that launched the projectile.
     */
    public ModifiedProjectile(JavaPlugin plugin, Projectile projectile, LivingEntity shooter) {
        this.projectile = projectile;
        this.shooter = shooter;
        this.plugin = plugin;
        onLaunch();
    }

    /**
     * Gets the velocity of the projectile.
     *
     * @return The velocity vector of the projectile.
     */
    public Vector getVelocity() {
        return projectile.getVelocity();
    }

    /**
     * Sets the speed of the projectile by modifying its velocity.
     *
     * @param speed The new speed of the projectile.
     */
    public void setSpeed(double speed) {
        double init = getVelocity().length();
        projectile.getVelocity().multiply(speed/init);
    }

    /**
     * Initiates the transformation of the projectile after launch.
     * Starts a BukkitRunnable task to handle the projectile's behavior during flight.
     */
    public void transform() {
        new BukkitRunnable() {
            public void run() {
                if(projectile.getLocation().getY() < -70) {
                    this.cancel();
                    onDestroy();
                }
                if(projectile.isValid()) {
                    onMove();
                } else {
                    this.cancel();
                    onDestroy();
                }
            }
        }.runTaskTimer(plugin, 1, 1);
    }

    /**
     * Called when the projectile is in motion during its flight.
     * Implement this method to handle actions when the projectile is moving.
     */
    public abstract void onMove();

    /**
     * Called when the projectile is launched.
     * Implement this method to handle actions when the projectile is launched.
     */
    public abstract void onLaunch();

    /**
     * Called when the projectile is destroyed or removed.
     * Implement this method to handle cleanup or additional actions when the projectile is destroyed.
     */
    public abstract void onDestroy();

}
