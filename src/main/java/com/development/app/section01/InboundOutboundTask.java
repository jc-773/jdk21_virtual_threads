package com.development.app.section01;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;

public class InboundOutboundTask {
    private static final Logger log = LoggerFactory.getLogger(InboundOutboundTask.class);
    private static final int MAX_PLATFORM = 10;
    private static final int MAX_VIRTUAL_PLATFORM = 10;

    public static void main(String[] args) throws InterruptedException {
       // virtualThreadCreation();
       System.out.println(
        AppUtils.timer(() -> Task.findFib(46))
       );
       
    }

    //in production environment would be using thread pool
    private static void platformThreadCreation() {
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
            new Thread(() -> {
                Task.ioIntesive(j);
            }).start();
        }
    }

    private static void platformThreadCreation2() {
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
           Thread thread = Thread.ofPlatform().unstarted(() -> Task.ioIntesive(j));
           thread.start();
        }
    }

    //this will create Daemon threads wich are background threads. They will not prevent the JVM from exiting the application
    private static void platformThreadCreation3() {
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
           Thread thread = Thread.ofPlatform().daemon().unstarted(() -> Task.ioIntesive(j));
           thread.start();
        }
    }

    //this will create Daemon threads but will wait for all of them to finish using CountDownLatch
    private static void platformThreadCreation4() throws InterruptedException {
        var latch = new CountDownLatch(MAX_PLATFORM);
        for (int i = 0; i < MAX_PLATFORM; i++) {
            int j = i;
           Thread thread = Thread.ofPlatform().daemon().unstarted(() -> {Task.ioIntesive(j); latch.countDown();});
           thread.start();
        }
        latch.await();
    }

    //Virtual threads cannot be created using the new keyword because it is a private package class
    // They are created using Thread.ofVirtual() method
    // Virtual threads are Daemon by default, so make use of the CountdownLatch class
    private static void virtualThreadCreation() throws InterruptedException {
        var builder = Thread.ofVirtual().name("virtualThread-", 1);
        log.info("memory address of builder: {}", builder);
        var latch = new CountDownLatch(MAX_VIRTUAL_PLATFORM);
        for (int i = 0; i < MAX_VIRTUAL_PLATFORM; i++) {
            int j = i;
           Thread thread = builder.unstarted(() -> {Task.ioIntesive(j); latch.countDown();});
           thread.start();
        }
        latch.await();
    }
}
