package com.development.app.section08;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;

/**
 * 
 * This lecture elaborates on the CompletableFuture classes capabilities 
 * 
 * Using the generic runAsync() method you can run a blocking asynchronous task
 *  - this is assuming the caller method won't need the result 
 * 
 * You can specify the type of Executor in the second argument of the runAsync() method
 *  - in this case below, we used a virtual thread
 * 
 * Oddly enough we can assign the runAsync result to a variable, despite it being void, and return a type CompletableFuture<Void>
 * 
 * This will be ro use the thenRun() method to verify that the task compelted successfully 
 * 
 */

public class Lec02RunAsync {
    private static final Logger log = LoggerFactory.getLogger(Lec02RunAsync.class);

    public static void main(String[] args) {
        log.info("main started");
        runAsync()
            .thenRun(() -> log.info("Done"))//what if task fails?
            .exceptionally(ex -> {
                log.info("error - {}", ex.getMessage());
                return null;
            });
    
        
        log.info("main ended");
        AppUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<Void> runAsync() {
        log.info("runAsync started");
        var completableFuture = CompletableFuture.runAsync(() -> { //non-blocking (with virtual threads) call 
            //AppUtils.sleep(Duration.ofSeconds(1));
            throw new RuntimeException();
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("runAsync ended");
        return completableFuture; //returns void CompletableFuture obj
    }
}
