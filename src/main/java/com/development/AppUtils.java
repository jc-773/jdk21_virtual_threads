package com.development;


public class AppUtils {
    public static long timer(Runnable runner) {
        var start = System.currentTimeMillis();
        runner.run();
        var end = System.currentTimeMillis();

        return end - start;
    }

}
