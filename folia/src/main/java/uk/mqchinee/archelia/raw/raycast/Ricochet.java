package uk.mqchinee.archelia.raw.raycast;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import uk.mqchinee.archelia.enums.Time;
import uk.mqchinee.archelia.utils.Experiments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class representing a ricochet projectile in the game world.
 * This class provides methods to handle the behavior of the ricochet during its flight.
 */
public abstract class Ricochet {

    @Getter @Setter private List<Material> allowedMaterials = new ArrayList<>();
    @Getter @Setter private Collection<EntityType> allowedEntities = Collections.emptyList();
    @Getter @Setter private int lifespan;
    @Getter @Setter private int delay;
    @Getter @Setter private Time type;
    @Getter private final Location location;
    @Getter @Setter private LivingEntity shooter = null;
    @Getter @Setter private Vector direction;
    @Getter private final JavaPlugin plugin;
    @Getter @Setter private double step;
    @Getter @Setter private Block endBlock;
    @Getter @Setter private Entity penetratedEntity;
    @Getter @Setter private double offsetX = 0.2;
    @Getter @Setter private double offsetY = 0.1;
    @Getter @Setter private double offsetZ = 0.2;
    @Getter @Setter private double weight = 0.5;
    private BukkitRunnable task;
    @Getter @Setter private boolean physics = false;
    @Getter @Setter private int max = 3;
    @Getter @Setter private int now = 0;
    @Getter @Setter private List<Block> bl = new ArrayList<>();

    /**
     * Constructor for the Ricochet class.
     *
     * @param plugin The JavaPlugin instance associated with this ricochet.
     * @param delay The delay in ticks between ricochet updates.
     * @param type The time type for the delay (in ticks or seconds).
     * @param step The distance step for each ricochet update.
     * @param lifespan The maximum lifespan of the ricochet.
     * @param location The starting location of the ricochet.
     * @param direction The direction vector of the ricochet.
     */
    public Ricochet(JavaPlugin plugin, int delay, Time type, double step, int lifespan, Location location, Vector direction) {
        this.type = type;
        this.lifespan = lifespan;
        this.location = location;
        this.direction = direction;
        this.plugin = plugin;
        this.step = step;
        if (this.type == Time.IN_TICKS) { this.delay = delay; }
        else { this.delay = delay*20; }
    }

    /**
     * Constructor for the Ricochet class when launched from a living entity.
     *
     * @param plugin The JavaPlugin instance associated with this ricochet.
     * @param delay The delay in ticks between ricochet updates.
     * @param type The time type for the delay (in ticks or seconds).
     * @param step The distance step for each ricochet update.
     * @param lifespan The maximum lifespan of the ricochet.
     * @param shooter The living entity that launched the ricochet.
     */
    public Ricochet(JavaPlugin plugin, int delay, Time type, double step, int lifespan, LivingEntity shooter) {
        this.type = type;
        this.lifespan = lifespan;
        this.shooter = shooter;
        this.location = shooter.getEyeLocation();
        this.direction = location.getDirection();
        this.plugin = plugin;
        this.step = step;

        if (this.type == Time.IN_TICKS) { this.delay = delay; }
        else { this.delay = delay*20; }
    }

    /**
     * Stops the ricochet by canceling the associated BukkitRunnable task.
     */
    public void stop() {
        Experiments.ignore(() -> task.cancel());
    }

    /**
     * Launches the ricochet.
     */
    public void launch() {
        onLaunch();
        new BukkitRunnable() {
            double a = 0;
            public void run() {
                task = this;
                if(location.getY() < -70) {
                    this.cancel();
                    onDestroy();
                }
                a = a + step;
                if (a > lifespan) {
                    onDestroy();
                    this.cancel();
                }
                double x = direction.getX() * a;
                double y;
                if(physics) {
                    y = direction.getY() * a + weight;
                } else {
                    y = direction.getY() * a;
                }
                double z = direction.getZ() * a;
                if (!allowedMaterials.contains(location.getBlock().getType())) {
                    endBlock = location.getBlock();
                    if (!allowedMaterials.contains(location.getBlock().getType())) {
                        location.add(flip(x), flip(y), flip(z));
                        if(bl.size() < 3) {
                            bl.add(location.getBlock());
                        }
                    }
                    ricochet(bl.get(0).getLocation());
                    bl.clear();
                    onRicochet();
                }
                else {
                    location.add(x, y, z);
                    onMove();
                }
                Collection<Entity> b = location.getWorld().getNearbyEntities(location, offsetX, offsetY, offsetZ);
                b.forEach((entity -> {
                    if(entity != shooter) {
                        if (!allowedEntities.contains(entity.getType())) {
                            penetratedEntity = entity;
                            onHitEntity();
                            this.cancel();
                        }
                        penetratedEntity = entity;
                        onPenetrateEntity();
                    }
                }));
            }

        }.runTaskTimer(plugin, delay, delay);
    }

    /**
     * Called when the ricochet is in motion during its flight.
     * Implement this method to handle actions when the ricochet is moving.
     */
    public abstract void onMove();

    /**
     * Called when the ricochet is destroyed or removed.
     * Implement this method to handle cleanup or additional actions when the ricochet is destroyed.
     */
    public abstract void onDestroy();

    /**
     * Called when the ricochet hits an obstacle and ricochets off it.
     * Implement this method to handle actions when the ricochet ricochets off an obstacle.
     */
    public abstract void onRicochet();

    /**
     * Called when the ricochet hits an entity.
     * Implement this method to handle actions when the ricochet hits an entity.
     */
    public abstract void onHitEntity();

    /**
     * Called when the ricochet is launched.
     * Implement this method to handle actions when the ricochet is launched.
     */
    public abstract void onLaunch();

    /**
     * Called when the ricochet penetrates an entity.
     * Implement this method to handle actions when the ricochet penetrates an entity.
     */
    public abstract void onPenetrateEntity();

    private boolean isNegative(double v) {
        return Double.doubleToRawLongBits(v) < 0;
    }

    private double flip(double coordinate) {
        if (isNegative(coordinate)) {
            return Math.abs(coordinate);
        }
        return -coordinate;
    }

    private void ricochet(Location loc) {
        BlockFace blockFace = face(loc);
        if (blockFace != null) {
            Vector dir = new Vector(blockFace.getModX(), blockFace.getModY(), blockFace.getModZ());
            dir = dir.multiply(direction.dot(dir)).multiply(2.0D);
            if (now != max) {
                setDirection(direction.subtract(dir).normalize().multiply(1));
                now++;
            }
            else { stop(); onDestroy(); }
        }
    }

    private BlockFace face(Location loc) {
        World world = loc.getWorld();
        if (world == null)
            return null;
        Block block = loc.getBlock();
        BlockIterator blockIterator = new BlockIterator(world, loc.toVector(), direction, 0.0D, 8);
        Block previousBlock = block;
        Block nextBlock = blockIterator.next();
        while (blockIterator.hasNext() && (nextBlock.getType() == Material.AIR || nextBlock.isLiquid() || nextBlock.equals(block))) {
            previousBlock = nextBlock;
            nextBlock = blockIterator.next();
        }
        BlockFace blockFace = nextBlock.getFace(previousBlock);
        return (blockFace == BlockFace.SELF) ? BlockFace.UP : blockFace;
    }

}
