package uk.mqchinee.archelia.utils;

import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import uk.mqchinee.archelia.Archelia;
import uk.mqchinee.archelia.enums.Time;

import java.util.concurrent.TimeUnit;

/**
 * Utility class for managing scheduled tasks in Bukkit.
 */
public class RunUtils {

    /**
     * Get the instance of the Archelia plugin.
     *
     * @return The Archelia plugin instance.
     */
    public static Archelia get() {
        return Archelia.getInstance();
    }

    /**
     * Get the Bukkit scheduler for task scheduling.
     *
     * @return The BukkitScheduler instance.
     */
    public static AsyncScheduler scheduler() {
        return Bukkit.getAsyncScheduler();
    }

    /**
     * Convert ticks to seconds.
     *
     * @param ticks The number of ticks to convert to seconds.
     * @return The equivalent time in seconds.
     */
    public static int toSeconds(int ticks) {
        return ticks * 20;
    }

    /**
     * Run a task asynchronously.
     *
     * @param r The Runnable task to run asynchronously.
     */
    public static void run(Runnable r) {
        scheduler().runNow(get(), (task) -> r.run());
    }

    /**
     * Run a task later with a specified delay.
     *
     * @param r    The Runnable task to run later.
     * @param wait The delay in ticks or seconds, based on the Time type.
     */
    public static void runLater(Runnable r, int wait) {
        int i = 0;
        while (i != wait) {
            i++;
        }
        scheduler().runNow(get(), (task) -> r.run());
    }

    /**
     * Run a task with a specified delay and period, creating a repeating timer.
     *
     * @param r      The Runnable task to run with a repeating timer.
     * @param delay  The initial delay before the first execution in ticks or seconds, based on the Time type.
     * @param timeUnit   The Time type indicating whether the delays are in ticks or seconds.
     * @return The task ID of the repeating timer.
     */
    public static int repeating(Runnable r, int delay, TimeUnit timeUnit) {
        scheduler().runDelayed(get(), (task) -> r.run(), delay, timeUnit);
        return 0;
    }
}
