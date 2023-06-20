package uk.mqchinee.featherapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import uk.mqchinee.featherapi.FeatherAPI;
import uk.mqchinee.featherapi.enums.Time;

public class RunUtils {
    public FeatherAPI get() {
        return FeatherAPI.get();
    }

    public BukkitScheduler scheduler() {
        return Bukkit.getScheduler();
    }

    public int toSeconds(int ticks) {
        return ticks*20;
    }

    public void async(Runnable r) {
        scheduler().runTaskAsynchronously(get(), r);
    }

    public void run(Runnable r) {
        scheduler().runTask(get(), r);
    }

    public void runLater(Runnable r, int wait, Time type) {
        switch (type) {
            case IN_TICKS:
                scheduler().runTaskLater(get(), r, wait);
            case IN_SECONDS:
                scheduler().runTaskLater(get(), r, toSeconds(wait));
        }
    }

    public void runTimer(Runnable r, int wait, Time type) {
        switch (type) {
            case IN_TICKS:
                scheduler().runTaskTimer(get(), r, wait, wait);
            case IN_SECONDS:
                scheduler().runTaskTimer(get(), r, toSeconds(wait), toSeconds(wait));
        }
    }

    public void runTimerAsync(Runnable r, int wait, Time type) {
        switch (type) {
            case IN_TICKS:
                scheduler().runTaskTimerAsynchronously(get(), r, wait, wait);
            case IN_SECONDS:
                scheduler().runTaskTimerAsynchronously(get(), r, toSeconds(wait), toSeconds(wait));
        }
    }

    public void runLaterAsync(Runnable r, int wait, Time type) {
        switch (type) {
            case IN_TICKS:
                scheduler().runTaskLaterAsynchronously(get(), r, wait);
            case IN_SECONDS:
                scheduler().runTaskLaterAsynchronously(get(), r, toSeconds(wait));
        }
    }

    public int repeating(Runnable r, int delay, int period, Time type) {
        switch (type) {
            case IN_TICKS:
                return scheduler().scheduleSyncRepeatingTask(get(), r, delay, period);
            case IN_SECONDS:
                return scheduler().scheduleSyncRepeatingTask(get(), r, toSeconds(delay), toSeconds(period));
        }
        return 0;
    }

}
