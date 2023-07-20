package uk.mqchinee.archelia.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import uk.mqchinee.archelia.Archelia;
import uk.mqchinee.archelia.enums.Time;

public class RunUtils {

    public static Archelia get() {
        return Archelia.getInstance();
    }

    public static BukkitScheduler scheduler() {
        return Bukkit.getScheduler();
    }

    public static int toSeconds(int ticks) {
        return ticks*20;
    }

    public static void async(Runnable r) {
        scheduler().runTaskAsynchronously(get(), r);
    }

    public static void run(Runnable r) {
        scheduler().runTask(get(), r);
    }

    public static void runLater(Runnable r, int wait, Time type) {
        switch (type) {
            case IN_TICKS:
                scheduler().runTaskLater(Archelia.getInstance(), r, wait);
            case IN_SECONDS:
                scheduler().runTaskLater(Archelia.getInstance(), r, toSeconds(wait));
        }
    }

    public static void runTimer(Runnable r, int wait, Time type) {
        switch (type) {
            case IN_TICKS:
                scheduler().runTaskTimer(get(), r, wait, wait);
            case IN_SECONDS:
                scheduler().runTaskTimer(get(), r, toSeconds(wait), toSeconds(wait));
        }
    }

    public static void runTimerAsync(Runnable r, int wait, Time type) {
        switch (type) {
            case IN_TICKS:
                scheduler().runTaskTimerAsynchronously(get(), r, wait, wait);
            case IN_SECONDS:
                scheduler().runTaskTimerAsynchronously(get(), r, toSeconds(wait), toSeconds(wait));
        }
    }

    public static void runLaterAsync(Runnable r, int wait, Time type) {
        switch (type) {
            case IN_TICKS:
                scheduler().runTaskLaterAsynchronously(get(), r, wait);
            case IN_SECONDS:
                scheduler().runTaskLaterAsynchronously(get(), r, toSeconds(wait));
        }
    }

    public static int repeating(Runnable r, int delay, int period, Time type) {
        return switch (type) {
            case IN_TICKS -> scheduler().scheduleSyncRepeatingTask(get(), r, delay, period);
            case IN_SECONDS -> scheduler().scheduleSyncRepeatingTask(get(), r, toSeconds(delay), toSeconds(period));
        };
    }

}
