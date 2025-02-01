package com.development.app.sandbox;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;

public class ThreadSandbox {
    private static final Logger log = LoggerFactory.getLogger(ThreadSandbox.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        /*
         * This code creates a new virtual thread per task and executes the tasks in order (i.e. task 3 will have to wait for blocking task1 and task2 to complete)
         */
        // try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
        //     executor.submit(() -> {
        //         task1();
        //         task2();
        //         task3();
        //     });
        // }
        asyncTask1()
            .thenRun(() -> {
                log.info("Task completed successfully");
            })
            .exceptionally(throwable -> {
                throwable.getMessage();
                return null;
            });

        // asyncTask2()
        //     .thenAccept((result) -> {
        //         log.info("Result; {}", result);
        //     })
        //     .exceptionally(throwable -> {
        //         throwable.getMessage();
        //         return null;
        //     });
        AppUtils.sleep(Duration.ofSeconds(3));
    }

    private static CompletableFuture<Void> asyncTask1() {
        var future = CompletableFuture.runAsync(() -> {
            AppUtils.sleep(Duration.ofSeconds(2));
            log.info("Async Task 1");
        }, Executors.newVirtualThreadPerTaskExecutor());
        return future;
    }

    private static CompletableFuture<String> asyncTask2() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            return "Async Task 2";
        });
        return future;
    }

    private static void task1() {
        AppUtils.sleep(Duration.ofSeconds(2));
        log.info("Task 1...");
    }

    private static void task2() {
        AppUtils.sleep(Duration.ofSeconds(2));
        log.info("Task 2...");
    }

    private static void task3() {
        AppUtils.sleep(Duration.ofSeconds(1));
        log.info("Task 3...");
    }
}
