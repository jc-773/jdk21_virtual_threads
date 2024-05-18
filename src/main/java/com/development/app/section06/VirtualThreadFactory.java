package com.development.app.section06;

import java.time.Duration;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;


public class VirtualThreadFactory {
     private static final Logger log = LoggerFactory.getLogger(VirtualThreadFactory.class);

    public static void demo(ThreadFactory factory) {
       int threadCount = 3;
        for (int i = 0; i < threadCount; i++) {
            var parentThread = factory.newThread(() -> {
                log.info("Task started: {}", Thread.currentThread());
                    var childThread = factory.newThread(() -> {
                        log.info("Child thread started: {}", Thread.currentThread());
                        AppUtils.sleep(Duration.ofSeconds(2));
                        log.info("Child thread ended: {}", Thread.currentThread());
                    });
                    childThread.start();
                    log.info("Task ended: {}", Thread.currentThread());
            });
            parentThread.start();
        }
    }

}
