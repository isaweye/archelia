package uk.mqchinee.archelia.utils;

import java.util.SplittableRandom;

/**
 * Utility class for running experiments and handling exceptions.
 */
public class Experiments {

    /**
     * Run the given task and ignore any exceptions that occur.
     *
     * @param runnable The task to run.
     */
    @Deprecated
    public static void ignore(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception ignore) {
            // Ignore any exceptions
        }
    }

    /**
     * Run the given task with a chance based on the provided percentage.
     *
     * @param percentage The chance in percentage (0 to 100) of running the task.
     * @param runnable The task to run.
     */
    public static void chance(int percentage, Runnable runnable) {
        SplittableRandom random = new SplittableRandom();
        if (random.nextInt(1, 101) <= percentage) {
            runnable.run();
        }
    }
}
