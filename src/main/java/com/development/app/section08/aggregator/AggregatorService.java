package com.development.app.section08.aggregator;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import com.development.app.section07.Client;

public class AggregatorService {
    public static ExecutorService executor;

    public AggregatorService(ExecutorService executor) {
        this.executor = executor;
    }

    public ProductDTO getProductDto(int id) {
        //a thread will come in and make 2 separate calls to get the product and the rating data respectively
        // One task gets it's own thread 
        var product = CompletableFuture.supplyAsync(() -> Client.getProduct(id), executor)
            .exceptionally(ex -> "Try a different product Id");
        //Another task gets it's own thread
        var rating = CompletableFuture.supplyAsync(() -> Client.getRating(id), executor)
            .exceptionally(ex -> "Try a different rating Id");
        return new ProductDTO(id, product.join(), rating.join());
    }
}
