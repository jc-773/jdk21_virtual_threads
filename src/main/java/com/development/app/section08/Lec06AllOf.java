package com.development.app.section08;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.app.section08.aggregator.AggregatorService;

public class Lec06AllOf {
    private static final Logger log = LoggerFactory.getLogger(Lec06AllOf.class);
    public static void main(String[] args) {
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);
        var futures = IntStream.rangeClosed(0, 50)
                .mapToObj(id -> CompletableFuture.supplyAsync(() -> aggregator.getProductDto(id), executor)).toList();

        CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join(); //this allOf() method will gather ALL of the CompletableFutures created in the stream above -which should be 50
        var list = futures.stream()
            .map(CompletableFuture::join)
            .toList();

        log.info("list:{}\nlist size: {}", list, list.size());
    }
}
