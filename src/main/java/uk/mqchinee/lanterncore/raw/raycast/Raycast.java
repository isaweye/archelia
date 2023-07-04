package uk.mqchinee.lanterncore.raw.raycast;

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
import uk.mqchinee.lanterncore.enums.Time;
import uk.mqchinee.lanterncore.utils.Experiments;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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

    public void stop() {
        Experiments.ignore(() -> task.cancel());
    }

    public void launch(boolean physics) {
        onLaunch();
        new BukkitRunnable() {
            double a = 0;
            public void run() {
                task = this;
                if(location.getY() < -70) {
                    this.cancel(); onDestroy();
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

    public abstract void onMove();
    public abstract void onDestroy();
    public abstract void onHitBlock();
    public abstract void onHitEntity();
    public abstract void onLaunch();
    public abstract void onPenetrateEntity();


}
