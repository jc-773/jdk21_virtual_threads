package com.development.app.section07.aggregator;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.development.app.section07.Client;

public class AggregatorService {
    
    public static ExecutorService executor;

    public AggregatorService(ExecutorService executor) {
        this.executor = executor;
    }

    public ProductDTO getProductDto(int id) throws ExecutionException, InterruptedException {
        //a thread will come in and make 2 separate calls to get the product and the rating data

        Future<String> product = executor.submit(() -> 
            Client.getProduct(id)
        );

        Future<String> rating = executor.submit(() -> 
            Client.getRating(id)
        );

        return new ProductDTO(id, product.get(), rating.get());
    }
}
