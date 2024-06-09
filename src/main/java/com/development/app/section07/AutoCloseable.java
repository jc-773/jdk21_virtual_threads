package com.development.app.section07;

import java.time.Duration;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;

public class AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(AutoCloseable.class);

    public static void main(String[] args) {
        // var executor = Executors.newSingleThreadExecutor();
        // executor.submit(() -> someTask());
        // log.info("submitted by executor");
        // executor.shutdown();

        //using try-with-resources automatically calls the executor.shutdown method
        try(var executor = Executors.newSingleThreadExecutor()) {
            executor.submit(() -> someTask());
            log.info("submitted by executor");
        }
    }

    public static void someTask() {
        AppUtils.sleep(Duration.ofSeconds(1));
        log.info("Task executed");
    }
}
