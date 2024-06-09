package com.development.app.sandbox;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadSandbox {
    private static final Logger log = LoggerFactory.getLogger(ThreadSandbox.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
       var executor = Executors.newVirtualThreadPerTaskExecutor();
       var executor2 = Executors.newVirtualThreadPerTaskExecutor();
       Future<String> name = executor.submit(() -> getName());
       Future<String> name2 = executor2.submit(() -> getName());
       log.info("String 1 {} String 2 {}", name, name2);
    }
    
    public static synchronized void doSomeTask() {
        for(int i = 0; i < 5; i++) {
            log.info("Did some task: {}", i);
        }
    }

    private static String getName() {
        for(int i = 0; i < 5; i++) {
            log.info("Got some name");
        }
        return "Some name";
    }
}
