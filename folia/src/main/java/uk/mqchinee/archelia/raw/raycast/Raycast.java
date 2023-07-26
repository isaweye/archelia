package uk.mqchinee.archelia.raw.raycast;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import uk.mqchinee.archelia.enums.Time;
import uk.mqchinee.archelia.utils.Experiments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Abstract class representing a raycast projectile in the game world.
 * This class provides methods to handle the behavior of the raycast during its flight.
 */
public abstract class Raycast {

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

    /**
     * Constructor for the Raycast class.
     *
     * @param plugin The JavaPlugin instance associated with this raycast.
     * @param delay The delay in ticks between raycast updates.
     * @param type The time type for the delay (in ticks or seconds).
     * @param step The distance step for each raycast update.
     * @param lifespan The maximum lifespan of the raycast.
     * @param location The starting location of the raycast.
     * @param direction The direction vector of the raycast.
     */
    public Raycast(JavaPlugin plugin, int delay, Time type, double step, int lifespan, Location location, Vector direction) {
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
     * Constructor for the Raycast class when launched from a living entity.
     *
     * @param plugin The JavaPlugin instance associated with this raycast.
     * @param delay The delay in ticks between raycast updates.
     * @param type The time type for the delay (in ticks or seconds).
     * @param step The distance step for each raycast update.
     * @param lifespan The maximum lifespan of the raycast.
     * @param shooter The living entity that launched the raycast.
     */
    public Raycast(JavaPlugin plugin, int delay, Time type, double step, int lifespan, LivingEntity shooter) {
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
     * Stops the raycast by canceling the associated BukkitRunnable task.
     */
    public void stop() {
        Experiments.ignore(() -> task.cancel());
    }

    /**
     * Launches the raycast.
     *
     * @param physics Whether the raycast is affected by physics (gravity).
     */
    public void launch(boolean physics) {
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
                double x = direction.getX() * a;
                double y;
                if(physics) {
                    y = direction.getY() * a + weight;
                } else {
                    y = direction.getY() * a;
                }
                double z = direction.getZ() * a;
                location.add(x, y, z);
                onMove();
                if (!allowedMaterials.contains(location.getBlock().getType())) {
                    endBlock = location.getBlock();
                    onHitBlock();
                    this.cancel();
                }
                Collection<Entity> b = location.getWorld().getNearbyEntities(location, offsetX, offsetY, offsetZ);
                b.forEach((entity -> {
                    if(entity != shooter) {
                        if (!allowedEntities.contains(entity.getType())) {
                            penetratedEntity = entity;
                            onHitEntity();
                            this.cancel();
                            return;
                        }
                        penetratedEntity = entity;
                        onPenetrateEntity();
                    }
                }));
                if (a > lifespan) {
                    onDestroy();
                    this.cancel();
                }
            }

        }.runTaskTimer(plugin, delay, delay);
    }

    /**
     * Called when the raycast is in motion during its flight.
     * Implement this method to handle actions when the raycast is moving.
     */
    public abstract void onMove();

    /**
     * Called when the raycast is destroyed or removed.
     * Implement this method to handle cleanup or additional actions when the raycast is destroyed.
     */
    public abstract void onDestroy();

    /**
     * Called when the raycast hits a block.
     * Implement this method to handle actions when the raycast hits a block.
     */
    public abstract void onHitBlock();

    /**
     * Called when the raycast hits an entity.
     * Implement this method to handle actions when the raycast hits an entity.
     */
    public abstract void onHitEntity();

    /**
     * Called when the raycast is launched.
     * Implement this method to handle actions when the raycast is launched.
     */
    public abstract void onLaunch();

    /**
     * Called when the raycast penetrates an entity.
     * Implement this method to handle actions when the raycast penetrates an entity.
     */
    public abstract void onPenetrateEntity();

}
