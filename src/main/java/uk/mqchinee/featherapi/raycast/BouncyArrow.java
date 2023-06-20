package uk.mqchinee.featherapi.raycast;

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

public abstract class BouncyArrow {

    private Projectile projectile;
    private LivingEntity shooter;
    private JavaPlugin plugin;
    private double reduce = 0.2;
    private double threshold;
    private BukkitRunnable task;
    private Projectile oldProjectile;

    public BouncyArrow(JavaPlugin plugin, Projectile projectile, double threshold, LivingEntity shooter) {
        this.projectile = projectile;
        this.shooter = shooter;
        this.plugin = plugin;
        this.threshold = threshold;
        this.oldProjectile = projectile;
    }

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
            }
            else { task.cancel(); projectile.remove(); onDestroy(); }
        }
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

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

    public LivingEntity getShooter() {
        return shooter;
    }

    public double getReduce() {
        return reduce;
    }

    public Projectile getProjectile() {
        return projectile;
    }

    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    public void setReduce(double reduce) {
        this.reduce = reduce;
    }

    public void setSpeed(double speed) {
        double init = getVelocity().length();
        projectile.getVelocity().multiply(speed/init);
    }

    public Vector getVelocity() {
        return projectile.getVelocity();
    }

    public void doBounce(Projectile projectile) {
            bounce(projectile);
            setSpeed(projectile.getVelocity().length() - threshold);
            onBounce();
    }

    public void transform() {
        new BukkitRunnable() {
            public void run() {
                task = this;
                if(projectile.getLocation().getY() < -10) {
                    this.cancel(); onDestroy();
                }
                if(!projectile.isOnGround() && !projectile.isDead()) {
                    onMove();
                    oldProjectile = projectile;
                }
                else if (projectile.isDead()) {
                    onEntityHit();
                }
                else {
                    doBounce(projectile);
                }
            }
        }.runTaskTimer(plugin, 1, 1);
    }

    public abstract void onMove();
    public abstract void onEntityHit();
    public abstract void onBounce();
    public abstract void onDestroy();

}
