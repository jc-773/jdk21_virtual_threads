package com.development.app.sandbox;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadLeakExample {
    private static final Logger log = LoggerFactory.getLogger(ThreadLeakExample.class);
    private static Semaphore semaphore;

    public ThreadLeakExample(int s) { //represents how many threads can access a resource concurrently 
        semaphore = new Semaphore(s);
    }

    public void mainTask() throws InterruptedException, ExecutionException {
        semaphore.acquire();
        try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<Integer> future = executor.submit(() -> {
                var n = subTask1(); 
                return subTask2(n); 
            });
            log.info("Future: {}", future.get());
        }
        semaphore.release();
    }

    private int subTask1() {
        return 1;
    }

    private int subTask2(int n) {
        log.info("subTask2's result: {}", n + 100);
        return n + 100;
    }
}
