package uk.mqchinee.featherapi.utils;

import java.util.SplittableRandom;

public class Experiments {

    public void ignore(Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception ignore) {}
    }

    public void chance(int integer, Runnable runnable) {
        SplittableRandom random = new SplittableRandom();
        if(random.nextInt(1, 101) <= integer) { runnable.run(); }
    }

}
