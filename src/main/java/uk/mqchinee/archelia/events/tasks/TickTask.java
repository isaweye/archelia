package uk.mqchinee.archelia.events.tasks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import uk.mqchinee.archelia.events.TickEvent;

public class TickTask extends BukkitRunnable {

    private final int ticks;

    public TickTask(int ticks) {
        this.ticks = ticks;
    }

    public BukkitTask runTask(JavaPlugin plugin) {
        return this.runTaskTimer(plugin, 0, ticks);
    }

    @Override
    public void run() {
        Bukkit.getServer().getPluginManager().callEvent(new TickEvent());
    }
}
