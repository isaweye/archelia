package uk.mqchinee.lanterncore.raw.raycast;

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
import uk.mqchinee.lanterncore.enums.Time;
import uk.mqchinee.lanterncore.utils.Experiments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    private boolean isNegative(double v) {
        return Double.doubleToRawLongBits(v) < 0;
    }

    private double flip(double coordinate) {
        if (isNegative(coordinate)) {
            return Math.abs(coordinate);
        }
        return -coordinate;
    }

    public void stop() {
        Experiments.ignore(() -> task.cancel());
    }

    public void launch() {
        onLaunch();
        new BukkitRunnable() {
            double a = 0;
            public void run() {
                task = this;
                if(location.getY() < -70) {
                    this.cancel(); onDestroy();
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

    public abstract void onMove();
    public abstract void onDestroy();
    public abstract void onRicochet();
    public abstract void onHitEntity();
    public abstract void onLaunch();
    public abstract void onPenetrateEntity();


}