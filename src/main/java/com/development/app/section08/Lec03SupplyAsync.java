package com.development.app.section08;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Lec03SupplyAsync {
    private static final Logger log = LoggerFactory.getLogger(Lec03SupplyAsync.class);
    public static void main(String[] args) {
        log.info("main started");
        runAsync()
            .thenAccept(result -> {
                log.info("Result: {}", result);
            });
    }

    private static CompletableFuture<String> runAsync() {
        log.info("runAsync started");
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            return "Hi Supply";
        }, Executors.newVirtualThreadPerTaskExecutor());
        log.info("runAsync ended");
        return completableFuture;
    }
}
