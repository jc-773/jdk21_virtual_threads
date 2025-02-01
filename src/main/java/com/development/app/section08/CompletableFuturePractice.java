package com.development.app.section08;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompletableFuturePractice {
    private static final Logger log = LoggerFactory.getLogger(CompletableFuturePractice.class);

    public static void main(String[] args) {
        // runAsyncVoid()
        //     .thenRun(() -> {
        //         log.info("Task completed");
        //     });

        runAsync()
            .thenAccept(result -> {
                log.info("Result: {}", result);
            });

    }

    public static CompletableFuture<Void> runAsyncVoid() {
        log.info("Run async void started");
        var completableFuture = CompletableFuture.runAsync(() -> {
            log.info("Run async void submitted");
        });
        log.info("Run async void completed");
        return completableFuture;
    }

    public static CompletableFuture<String> runAsync() {
        var completableFuture = CompletableFuture.supplyAsync(() -> {
            return "Completable Returned Value";
        });
        return completableFuture;
    }
}
