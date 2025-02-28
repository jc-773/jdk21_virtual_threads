package com.development.app.section08;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;

public class Lec08ThenCombine {
    private static final Logger log = LoggerFactory.getLogger(Lec08ThenCombine.class);
    record Airline(String airline, int amount) {}

    public static void main(String[] args) {
         try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            var delta = getDeltaFair(executor);
            var frontier = getFrontierFair(executor);
            var bestDeal = delta.thenCombine(frontier, (a,b) -> a.amount < b.amount ? a : b )
            .thenApply(airfare -> new Airline(airfare.airline(), (int) (airfare.amount() * .9))).join();
            log.info("Best deal: {}", bestDeal);
        }
    }

    private static CompletableFuture <Airline> getDeltaFair(Executor executor) {
        return CompletableFuture.supplyAsync(() ->  {
            var random = ThreadLocalRandom.current().nextInt(0,500);
            AppUtils.sleep(Duration.ofMillis(225));
            return new Airline("Delta", random);
        }, executor);
        
    }

    private static CompletableFuture <Airline> getFrontierFair(Executor executor) {
        return CompletableFuture.supplyAsync(() -> {
            var random = ThreadLocalRandom.current().nextInt(0,350);
            AppUtils.sleep(Duration.ofMillis(225));
            return new Airline("Frontier", random);
        }, executor);
    }
}
