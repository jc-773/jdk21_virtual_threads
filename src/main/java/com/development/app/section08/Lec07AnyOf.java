package com.development.app.section08;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;

public class Lec07AnyOf {
    private static final Logger log = LoggerFactory.getLogger(Lec07AnyOf.class);
    
    public static void main(String[] args) {
        try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var delta = getDeltaFair(executor);
            var frontier = getFrontierFair(executor);
            log.info("Price: {}", CompletableFuture.anyOf(delta, frontier).join());
        }
    }

    private static CompletableFuture <String> getDeltaFair(Executor executor) {
        return CompletableFuture.supplyAsync(() ->  {
            var random = ThreadLocalRandom.current().nextInt(0,500);
            AppUtils.sleep(Duration.ofMillis(225));
            return "Delta-$" + random;
        }, executor);
        
    }

    private static CompletableFuture <String> getFrontierFair(Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(0,350);
            AppUtils.sleep(Duration.ofMillis(225));
            return "Frontier-$" + random;
        }, executor);
    }
}
