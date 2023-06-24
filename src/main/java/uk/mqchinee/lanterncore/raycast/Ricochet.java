package uk.mqchinee.lanterncore.raycast;

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

    private List<Material> allowedMaterials = new ArrayList<>();
    private Collection<EntityType> allowedEntities = Collections.emptyList();
    private int lifespan;
    private int delay;
    private Time type;
    private Location location;
    private LivingEntity shooter = null;
    private Vector direction;
    private JavaPlugin plugin;
    private double step;
    private Block endBlock;
    private Entity penetratedEntity;
    private double offsetX = 0.2;
    private double offsetY = 0.1;
    private double offsetZ = 0.2;
    private double weight = 0.5;
    private BukkitRunnable task;
    private boolean physics = false;
    private int max = 3;
    private int now = 0;
    private Block last;
    private List<Block> bl = new ArrayList<>();

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

    public boolean getPhysics() {
        return physics;
    }

    public void setPhysics(boolean physics) {
        this.physics = physics;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
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

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public double getOffsetZ() {
        return offsetZ;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public void setOffsetZ(double offsetZ) {
        this.offsetZ = offsetZ;
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
                if(location.getY() < -10) {
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

    public Block getBlock() {
        return endBlock;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public Entity getPenetratedEntity() {
        return penetratedEntity;
    }

    public LivingEntity getShooter() {
        return shooter;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public int getLifespan() {
        return lifespan;
    }

    public int getDelay() {
        return delay;
    }

    public Time getType() {
        return type;
    }

    public Location getLocation() {
        return location;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setAllowedEntities(List<EntityType> allowedEntities) {
        this.allowedEntities = allowedEntities;
    }

    public List<Material> getAllowedMaterials() {
        return allowedMaterials;
    }

    public Collection<EntityType> getAllowedEntities() {
        return allowedEntities;
    }

    public void setAllowedMaterials(List<Material> allowedMaterials) {
        this.allowedMaterials = allowedMaterials;
    }

    public void setLifespan(int lifespan) {
        this.lifespan = lifespan;
    }

    public void setType(Time type) {
        this.type = type;
    }

    public abstract void onMove();
    public abstract void onDestroy();
    public abstract void onRicochet();
    public abstract void onHitEntity();
    public abstract void onLaunch();
    public abstract void onPenetrateEntity();


}