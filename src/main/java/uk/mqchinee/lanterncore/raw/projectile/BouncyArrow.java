package uk.mqchinee.lanterncore.raw.projectile;

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

public abstract class BouncyArrow {

    @Getter @Setter private Projectile projectile;
    @Getter private final LivingEntity shooter;
    @Getter private final JavaPlugin plugin;
    @Getter @Setter private double reduce = 0.2;
    @Getter @Setter private double threshold;
    private BukkitRunnable task;

    public BouncyArrow(JavaPlugin plugin, Projectile projectile, double threshold, LivingEntity shooter) {
        this.projectile = projectile;
        this.shooter = shooter;
        this.plugin = plugin;
        this.threshold = threshold;
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


    public Vector getVelocity() {
        return projectile.getVelocity();
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

    public void setSpeed(double speed) {
        double init = getVelocity().length();
        projectile.getVelocity().multiply(speed/init);
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
                if(projectile.getLocation().getY() < -70) {
                    this.cancel(); onDestroy();
                }
                if(!projectile.isOnGround() && !projectile.isDead()) {
                    onMove();
                }
                else if (projectile.isDead()) {
                    onEntityHit();
                    this.cancel();
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