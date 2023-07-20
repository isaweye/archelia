package uk.mqchinee.archelia.raw.projectile;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public abstract class ModifiedProjectile {

    @Getter
    @Setter
    private Projectile projectile;
    @Getter private final LivingEntity shooter;
    @Getter private final JavaPlugin plugin;

    public ModifiedProjectile(JavaPlugin plugin, Projectile projectile, LivingEntity shooter) {
        this.projectile = projectile;
        this.shooter = shooter;
        this.plugin = plugin;
        onLaunch();
    }

    public Vector getVelocity() {
        return projectile.getVelocity();
    }

    public void setSpeed(double speed) {
        double init = getVelocity().length();
        projectile.getVelocity().multiply(speed/init);
    }

    public void transform() {
        new BukkitRunnable() {
            public void run() {
                if(projectile.getLocation().getY() < -70) {
                    this.cancel(); onDestroy();
                }
                if(projectile.isValid()) {
                    onMove();
                }
                else {
                    this.cancel(); onDestroy();
                }
            }
        }.runTaskTimer(plugin, 1, 1);
    }

    public abstract void onMove();
    public abstract void onLaunch();
    public abstract void onDestroy();

}
