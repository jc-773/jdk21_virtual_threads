package com.development;

import java.time.Duration;

public class AppUtils {
    public static long timer(Runnable runner) {
        var start = System.currentTimeMillis();
        runner.run();
        var end = System.currentTimeMillis();

        return end - start;
    }

    public static void sleep(Duration time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
