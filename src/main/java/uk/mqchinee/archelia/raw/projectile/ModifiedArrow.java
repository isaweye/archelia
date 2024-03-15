package uk.mqchinee.archelia.raw.projectile;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

/**
 * Abstract class representing a bouncy arrow projectile.
 * This class provides methods to handle bouncing behavior for the arrow.
 */
public abstract class ModifiedArrow {

    @Getter @Setter private Projectile projectile;
    @Getter private final LivingEntity shooter;
    @Getter private final JavaPlugin plugin;
    private BukkitRunnable task;

    /**
     * Constructor for the BouncyArrow class.
     *
     * @param plugin The JavaPlugin instance associated with this bouncy arrow.
     * @param projectile The projectile entity representing the arrow.
     * @param shooter The living entity that shot the arrow.
     */
    public ModifiedArrow(JavaPlugin plugin, Projectile projectile, LivingEntity shooter) {
        this.projectile = projectile;
        this.shooter = shooter;
        this.plugin = plugin;
    }

    /**
     * Gets the velocity of the arrow.
     *
     * @return The velocity vector of the arrow.
     */
    public Vector getVelocity() {
        return projectile.getVelocity();
    }

    /**
     * Sets the speed of the arrow by modifying its velocity.
     *
     * @param speed The new speed of the arrow.
     */
    public void setSpeed(double speed) {
        double init = getVelocity().length();
        projectile.getVelocity().multiply(speed/init);
    }

    /**
     * Initiates the transformation of the arrow.
     * Starts a BukkitRunnable task to handle arrow movement and bouncing behavior.
     */
    public void transform() {
        new BukkitRunnable() {
            public void run() {
                task = this;
                if(projectile.getLocation().getY() < -70) {
                    onDestroy();
                    this.cancel();
                }
                if(!projectile.isOnGround() && !projectile.isDead()) {
                    onMove();
                } else if (projectile.isDead()) {
                    onEntityHit();
                    this.cancel();
                } else {
                    onDestroy();
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 1, 1);
    }

    /**
     * Called when the arrow moves.
     * Implement this method to handle actions when the arrow is in motion.
     */
    public abstract void onMove();

    /**
     * Called when the arrow hits an entity.
     * Implement this method to handle actions when the arrow hits an entity.
     */
    public abstract void onEntityHit();

    /**
     * Called when the arrow is destroyed or removed.
     * Implement this method to handle cleanup or additional actions when the arrow is destroyed.
     */
    public abstract void onDestroy();

}
