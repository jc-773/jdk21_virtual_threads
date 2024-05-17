package com.development.app.section03;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.app.section01.InboundOutboundTask;
import com.development.app.section01.Task;

public class CPUTaskDemo {

    private static final int TASK_COUNT = Runtime.getRuntime().availableProcessors();
    private static final Logger log = LoggerFactory.getLogger(InboundOutboundTask.class);

    public static void main(String[] args) {
        demo(Thread.ofVirtual());
    }

    private static void demo(Thread.Builder builder) {
        // virtual threads are deamon threads by default, and because of that,
        // the application does NOT wait on the thread to complete it's tasks before exiting
        // so... we have to create a countdown latch that blocks until the await 
        var latch = new CountDownLatch(TASK_COUNT);
        for (int i = 1; i <= TASK_COUNT; i++) {
            builder.start(() -> {
                Task.cpuIntensive(45);
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

   
}
