package com.development.app.section01;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;

public class Task {

    private static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void cpuIntensive(int i) {
        log.info("Starting CPU task, Thread info: {}", Thread.currentThread());
        var timeTaken = AppUtils.timer(() -> Task.findFib(i));
        log.info("Ending CPU task, time taken: {} ms", timeTaken);
    }
    
    public static void ioIntesive(int i) {
        try {
            log.info("Starting I/O task {}. Thread info {}", i, Thread.currentThread());
            Thread.sleep(Duration.ofSeconds(10));
            log.info("Ending I/O task {}. Thread info {}", i, Thread.currentThread());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static long findFib(long input) {
        if (input < 2)
            return input;
        return findFib(input - 1) + findFib(input - 2);
    }

}
