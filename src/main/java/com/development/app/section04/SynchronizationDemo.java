package com.development.app.section04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SynchronizationDemo {
   private static final Logger log = LoggerFactory.getLogger(SynchronizationDemo.class);
   private static final List<Integer> list = new ArrayList<>();

   public static void main(String[] args) {
        demo(Thread.ofPlatform());
        log.info("List size: {}", list.size());
   }

    public static void demo(Thread.Builder builder) {
       int threadCount = 50;
       var countdownLatch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            builder.start(() -> {
                log.info("Task started: {}", Thread.currentThread());
                for (int j = 0; j < 200; j++) {
                    someTask(j);
                }
                log.info("Task ended: {}", Thread.currentThread());
            });
            countdownLatch.countDown();
        }
        try {
            countdownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //TODO: Needs to be thread locked
    public static synchronized void someTask(int i) {
        list.add(i);
    }



}
