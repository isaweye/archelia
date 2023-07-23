package uk.mqchinee.archelia.raw.projectile;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

/**
 * Abstract class representing a bouncy arrow projectile.
 * This class provides methods to handle bouncing behavior for the arrow.
 */
public abstract class BouncyArrow {

    @Getter @Setter private Projectile projectile;
    @Getter private final LivingEntity shooter;
    @Getter private final JavaPlugin plugin;
    @Getter @Setter private double reduce = 0.2;
    @Getter @Setter private double threshold;
    private BukkitRunnable task;

    /**
     * Constructor for the BouncyArrow class.
     *
     * @param plugin The JavaPlugin instance associated with this bouncy arrow.
     * @param projectile The projectile entity representing the arrow.
     * @param threshold The threshold velocity below which the arrow will be destroyed.
     * @param shooter The living entity that shot the arrow.
     */
    public BouncyArrow(JavaPlugin plugin, Projectile projectile, double threshold, LivingEntity shooter) {
        this.projectile = projectile;
        this.shooter = shooter;
        this.plugin = plugin;
        this.threshold = threshold;
    }

    /**
     * Handles the bouncing behavior of the arrow.
     * When the arrow collides with a block, it will bounce off it with reduced velocity.
     * If the arrow's velocity falls below the threshold, it will be destroyed.
     *
     * @param projectile The projectile entity representing the arrow.
     */
    private void bounce(Projectile projectile) {
        BlockFace blockFace = face(projectile);
        if (blockFace != null) {
            Vector dir = new Vector(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
            dir = dir.multiply(projectile.getVelocity().dot(dir)).multiply(2.0D);
            if (getVelocity().length() > threshold) {
                Projectile newProjectile = (Projectile) projectile.getWorld().spawnEntity(projectile.getLocation(), projectile.getType());
                newProjectile.setVelocity(projectile.getVelocity().subtract(dir).normalize().multiply(getVelocity().length()));
                newProjectile.setShooter(projectile.getShooter());
                if (projectile.isVisualFire()) { newProjectile.setFireTicks(50); }
                Projectile old = projectile;
                setProjectile(newProjectile);
                old.remove();
            } else {
                task.cancel();
                projectile.remove();
                onDestroy();
            }
        }
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
     * Determines the block face with which the arrow is colliding.
     * If the arrow is not colliding with any block, null is returned.
     *
     * @param projectile The projectile entity representing the arrow.
     * @return The block face with which the arrow is colliding, or null if there is no collision.
     */
    private BlockFace face(Projectile projectile) {
        World world = projectile.getLocation().getWorld();
        if (world == null)
            return null;
        Block block = projectile.getLocation().getBlock();
        BlockIterator blockIterator = new BlockIterator(world, projectile.getLocation().toVector(), projectile.getVelocity(), 0.0D, 3);
        Block previousBlock = block;
        Block nextBlock = blockIterator.next();
        while (blockIterator.hasNext() && (nextBlock.getType() == Material.AIR || nextBlock.isLiquid() || nextBlock.equals(block))) {
            previousBlock = nextBlock;
            nextBlock = blockIterator.next();
        }
        BlockFace blockFace = nextBlock.getFace(previousBlock);
        return (blockFace == BlockFace.SELF) ? BlockFace.UP : blockFace;
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
     * Performs the bounce action for the arrow.
     *
     * @param projectile The projectile entity representing the arrow.
     */
    public void doBounce(Projectile projectile) {
        bounce(projectile);
        setSpeed(projectile.getVelocity().length() - threshold);
        onBounce();
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
                    this.cancel();
                    onDestroy();
                }
                if(!projectile.isOnGround() && !projectile.isDead()) {
                    onMove();
                } else if (projectile.isDead()) {
                    onEntityHit();
                    this.cancel();
                } else {
                    doBounce(projectile);
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
     * Called when the arrow bounces off a block.
     * Implement this method to handle actions when the arrow bounces off a block.
     */
    public abstract void onBounce();

    /**
     * Called when the arrow is destroyed or removed.
     * Implement this method to handle cleanup or additional actions when the arrow is destroyed.
     */
    public abstract void onDestroy();

}
