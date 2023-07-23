package uk.mqchinee.archelia.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import uk.mqchinee.archelia.Archelia;
import uk.mqchinee.archelia.enums.Time;

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
    public static BukkitScheduler scheduler() {
        return Bukkit.getScheduler();
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
    public static void async(Runnable r) {
        scheduler().runTaskAsynchronously(get(), r);
    }

    /**
     * Run a task synchronously.
     *
     * @param r The Runnable task to run synchronously.
     */
    public static void run(Runnable r) {
        scheduler().runTask(get(), r);
    }

    /**
     * Run a task later with a specified delay.
     *
     * @param r    The Runnable task to run later.
     * @param wait The delay in ticks or seconds, based on the Time type.
     * @param type The Time type indicating whether the delay is in ticks or seconds.
     */
    public static void runLater(Runnable r, int wait, Time type) {
        switch (type) {
            case IN_TICKS -> scheduler().runTaskLater(Archelia.getInstance(), r, wait);
            case IN_SECONDS -> scheduler().runTaskLater(Archelia.getInstance(), r, toSeconds(wait));
        }
    }

    /**
     * Run a task with a specified delay and period, creating a repeating timer.
     *
     * @param r      The Runnable task to run with a repeating timer.
     * @param delay  The initial delay before the first execution in ticks or seconds, based on the Time type.
     * @param period The delay between consecutive executions in ticks or seconds, based on the Time type.
     * @param type   The Time type indicating whether the delays are in ticks or seconds.
     * @return The task ID of the repeating timer.
     */
    public static int repeating(Runnable r, int delay, int period, Time type) {
        return switch (type) {
            case IN_TICKS -> scheduler().scheduleSyncRepeatingTask(get(), r, delay, period);
            case IN_SECONDS -> scheduler().scheduleSyncRepeatingTask(get(), r, toSeconds(delay), toSeconds(period));
        };
    }

    /**
     * Run a task with a specified delay and period asynchronously, creating a repeating timer.
     *
     * @param r      The Runnable task to run with a repeating timer.
     * @param delay  The initial delay before the first execution in ticks or seconds, based on the Time type.
     * @param period The delay between consecutive executions in ticks or seconds, based on the Time type.
     * @param type   The Time type indicating whether the delays are in ticks or seconds.
     * @return The task ID of the repeating timer.
     */
    public static int repeatingAsync(Runnable r, int delay, int period, Time type) {
        switch (type) {
            case IN_TICKS -> scheduler().runTaskTimerAsynchronously(get(), r, delay, period);
            case IN_SECONDS -> scheduler().runTaskTimerAsynchronously(get(), r, toSeconds(delay), toSeconds(period));
        }
        return 0;
    }

    /**
     * Run a task later asynchronously with a specified delay.
     *
     * @param r    The Runnable task to run later.
     * @param wait The delay in ticks or seconds, based on the Time type.
     * @param type The Time type indicating whether the delay is in ticks or seconds.
     */
    public static void runLaterAsync(Runnable r, int wait, Time type) {
        switch (type) {
            case IN_TICKS -> scheduler().runTaskLaterAsynchronously(get(), r, wait);
            case IN_SECONDS -> scheduler().runTaskLaterAsynchronously(get(), r, toSeconds(wait));
        }
    }

    /**
     * Run a task with a specified delay and period asynchronously, creating a repeating timer.
     *
     * @param r      The Runnable task to run with a repeating timer.
     * @param delay  The initial delay before the first execution in ticks or seconds, based on the Time type.
     * @param period The delay between consecutive executions in ticks or seconds, based on the Time type.
     * @param type   The Time type indicating whether the delays are in ticks or seconds.
     * @return The task ID of the repeating timer.
     */
    public static int runTimerAsync(Runnable r, int delay, int period, Time type) {
        switch (type) {
            case IN_TICKS -> scheduler().runTaskTimerAsynchronously(get(), r, delay, period);
            case IN_SECONDS -> scheduler().runTaskTimerAsynchronously(get(), r, toSeconds(delay), toSeconds(period));
        }
        return 0;
    }
}
