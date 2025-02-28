package com.development.app.section09;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;
import com.development.app.section07.Client;
import com.development.app.section07.aggregator.ProductDTO;
import com.development.app.section08.Lec08ThenCombine;

public class Lec05StructuredScopedValues {
    private static final Logger log = LoggerFactory.getLogger(Lec05StructuredScopedValues.class);
    record Airline(String airline, int price) {}

    public static void main(String[] args) throws NumberFormatException, InterruptedException, ExecutionException {
        //this would be the way to do it in the past without StructureTaskScope
        // try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
        //     var name = executor.submit(() -> Client.getProduct(1));
        //     var rating = executor.submit(() -> Client.getRating(ThreadLocalRandom.current().nextInt(1,10)));
        //     var product = new ProductDTO(Integer.parseInt(rating.get().toString()), "product", name.get());
        // }

        //with StructuredTaskScope
        try(var taskScope = new StructuredTaskScope<>()) {
            var fair = taskScope.fork(() -> getDeltaFair());
            var failing = taskScope.fork(() -> failingTask());
            taskScope.join();
            
            log.info("subTask1 state--{}", fair.state());
            log.info("subTask2 state--{}", failing.state());

            // log.info("subTask1 result--{}", fair.get());
            // log.info("subTask2 result--{}", failing.get());
        }
    }

     private static String getDeltaFair() {
            var random = ThreadLocalRandom.current().nextInt(0,500);
            AppUtils.sleep(Duration.ofMillis(225));
            return "Delta-$" + random;
    }

    private static String failingTask() {
        throw new RuntimeException("oops");
    }
}
