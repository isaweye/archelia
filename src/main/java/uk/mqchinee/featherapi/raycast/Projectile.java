package uk.mqchinee.featherapi.raycast;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import uk.mqchinee.featherapi.raycast.events.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Projectile {

    private List<EntityType> entityTypes = new ArrayList<>();

    private List<Entity> entities = new ArrayList<>();

    private List<Material> materials = new ArrayList<>();

    private ParticleColor particle_color;

    private Projectile projectile;

    private Particle particle_type;

    private double max_distance;

    private double particle_speed;

    private double projectile_mass;

    private double hitbox;

    private JavaPlugin plugin;

    public Projectile(JavaPlugin plugin, Particle particle, double size, double mass, double speed, double distance) {
        this.particle_type = particle;
        this.projectile_mass = mass;
        this.particle_speed = speed;
        this.max_distance = distance;
        this.hitbox = size;
        this.particle_color = new ParticleColor(0, 0, 0);
        this.projectile = this;
        this.plugin = plugin;
    }

    public final void launch(LivingEntity who) {
        launch(who, who.getWorld(), new Vector(0, 0, 0), true);
    }

    public final void launch(LivingEntity who, boolean physics) {
        launch(who, who.getWorld(), new Vector(0, 0, 0), physics);
    }

    public final void launch(LivingEntity who, World where, boolean physics) {
        launch(who, where, new Vector(0, 0, 0), physics);
    }

    public final void launch(LivingEntity who, World where, Vector end_offset, boolean physics) {
        launch(who, where, new Vector(0, 0, 0), end_offset, physics);
    }

    public final void launch(LivingEntity who, World where, Vector start_offset, Vector end_offset, boolean physics) {
        launch(who, where, who.getEyeLocation(), start_offset, end_offset, physics);
    }

    public final void launch(LivingEntity who, World where, Location start, Vector start_offset, Vector end_offset, boolean physics) {
        launch(who, where, start.toVector(), start.getDirection(), start_offset, end_offset, physics);
    }

    public final void launch(final LivingEntity who, final World where, final Vector start, final Vector direction, Vector start_offset, Vector end_offset, final boolean physics) {
        this.projectile = this;
        start.add(direction.clone().normalize()).add(start_offset);
        final Location start_location = start.toLocation(where).setDirection(direction);
        final Vector end = start.clone().add(direction.clone().normalize().multiply(this.max_distance)).add(end_offset);
        (new BukkitRunnable() {
            double red;

            double green;

            double blue;

            double g;

            double vt;

            double t;

            double vo;

            double f;

            double a;

            Vector dir;

            Location previous;

            public void run() {
                if (Projectile.this.particle_type.equals(Particle.SPELL_MOB) || Projectile.this.particle_type.equals(Particle.SPELL_MOB_AMBIENT) || Projectile.this.particle_type.equals(Particle.REDSTONE)) {
                    this.red = (Projectile.this.particle_color.getRed() + 1.0D) / 255.0D;
                    this.green = Projectile.this.particle_color.getGreen() / 255.0D;
                    this.blue = Projectile.this.particle_color.getBlue() / 255.0D;
                }
                Location lerp = start.clone().add(end.clone().subtract(start).multiply(this.t)).toLocation(where).setDirection(direction);
                if (physics)
                    end.subtract(new Vector(this.dir.getX() * this.a, this.g, this.dir.getZ() * this.a));
                this.t += this.vo / Projectile.this.max_distance;
                if (Projectile.this.particle_type.equals(Particle.SPELL_MOB) || Projectile.this.particle_type.equals(Particle.SPELL_MOB_AMBIENT) || Projectile.this.particle_type.equals(Particle.REDSTONE)) {
                    lerp.getWorld().spawnParticle(Projectile.this.particle_type, lerp, 0, this.red, this.green, this.blue, 1.0D);
                } else {
                    lerp.getWorld().spawnParticle(Projectile.this.particle_type, lerp, 0, 0.0D, 0.0D, 0.0D, 0.0D);
                }
                if (Math.abs(this.t) >= 1.0D) {
                    ProjectileEvent event = new ProjectileEvent(who, start_location, lerp, Projectile.this.projectile);
                    Bukkit.getServer().getPluginManager().callEvent(event);
                    if (!event.isCancelled()) {
                        Projectile.this.OnHit(who, start_location, lerp.clone());
                        cancel();
                    }
                } else if (lerp.getBlock().getType() != Material.AIR) {
                    if (!Projectile.this.materials.contains(lerp.getBlock().getType())) {
                        ProjectileHitBlockEvent ProjectileHitBlockEvent = new ProjectileHitBlockEvent(who, start_location, lerp, lerp.getBlock(), Projectile.this.projectile);
                        Bukkit.getServer().getPluginManager().callEvent(ProjectileHitBlockEvent);
                        if (!ProjectileHitBlockEvent.isCancelled()) {
                            Projectile.this.OnHitBlock(who, start_location, lerp.clone(), lerp.getBlock());
                            cancel();
                        }
                    } else if (Projectile.this.materials.contains(lerp.getBlock().getType())) {
                        ProjectilePenetrateBlockEvent ProjectilePenetrateBlockEvent = new ProjectilePenetrateBlockEvent(who, start_location, lerp, lerp.getBlock(), Projectile.this.projectile);
                        Bukkit.getServer().getPluginManager().callEvent(ProjectilePenetrateBlockEvent);
                        if (!ProjectilePenetrateBlockEvent.isCancelled())
                            Projectile.this.OnPenetrateBlock(who, lerp.clone(), lerp.getBlock());
                    }
                } else if (!lerp.getWorld().getNearbyEntities(lerp, Projectile.this.hitbox, Projectile.this.hitbox, Projectile.this.hitbox).isEmpty() &&
                        lerp.getWorld().getNearbyEntities(lerp, Projectile.this.hitbox, Projectile.this.hitbox, Projectile.this.hitbox).iterator().hasNext()) {
                    Entity entity = lerp.getWorld().getNearbyEntities(lerp, Projectile.this.hitbox, Projectile.this.hitbox, Projectile.this.hitbox).iterator().next();
                    if (!Projectile.this.entities.contains(entity) && !Projectile.this.entityTypes.contains(entity.getType())) {
                        ProjectileHitEntityEvent ProjectileHitEntityEvent = new ProjectileHitEntityEvent(who, start_location, lerp, entity, Projectile.this.projectile);
                        Bukkit.getServer().getPluginManager().callEvent(ProjectileHitEntityEvent);
                        if (!ProjectileHitEntityEvent.isCancelled()) {
                            Projectile.this.OnHitEntity(who, start_location, lerp.clone(), entity);
                            cancel();
                        }
                    } else if (Projectile.this.entities.contains(entity) || Projectile.this.entityTypes.contains(entity.getType())) {
                        ProjectilePenetrateEntityEvent ProjectilePenetrateEntityEvent = new ProjectilePenetrateEntityEvent(who, start_location, lerp, entity, Projectile.this.projectile);
                        Bukkit.getServer().getPluginManager().callEvent(ProjectilePenetrateEntityEvent);
                        if (!ProjectilePenetrateEntityEvent.isCancelled())
                            Projectile.this.OnPenetrateEntity(who, lerp.clone(), entity);
                    }
                }
                Projectile.this.OnMove(this.previous, lerp, this.t);
                this.previous = lerp;
            }
        }).runTaskTimer(plugin, 0L, 1L);
    }

    public final void ignoreEntityType(EntityType type) {
        if (!this.entityTypes.contains(type))
            this.entityTypes.add(type);
    }

    public final void ignoreEntity(Entity entity) {
        if (!this.entities.contains(entity))
            this.entities.add(entity);
    }

    public final void ignoreMaterial(Material type) {
        if (!this.materials.contains(type))
            this.materials.add(type);
    }

    public final void setColor(ParticleColor color) {
        this.particle_color = color;
    }

    public final ParticleColor getColor() {
        return this.particle_color;
    }

    public final double getVelocity() {
        return this.particle_speed / 20.0D;
    }

    public void OnHit(LivingEntity who, Location start, Location end) {}

    public void OnHitBlock(LivingEntity who, Location start, Location end, Block block) {}

    public void OnHitEntity(LivingEntity who, Location start, Location end, Entity entity) {}

    public void OnMove(Location previous, Location current, double step) {}

    public void OnPenetrateBlock(LivingEntity who, Location where, Block block) {}

    public void OnPenetrateEntity(LivingEntity who, Location where, Entity entity) {}
}
