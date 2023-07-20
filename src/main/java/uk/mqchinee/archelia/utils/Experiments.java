package uk.mqchinee.archelia.utils;

import java.util.SplittableRandom;

public class Experiments {

    public static void ignore(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception ignore) {}
    }

    public static void chance(int integer, Runnable runnable) {
        SplittableRandom random = new SplittableRandom();
        if(random.nextInt(1, 101) <= integer) { runnable.run(); }
    }

}
