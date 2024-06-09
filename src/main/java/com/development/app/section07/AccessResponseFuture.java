package com.development.app.section07;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.app.section07.aggregator.AggregatorService;
import com.development.app.section07.aggregator.ProductDTO;

public class AccessResponseFuture {
    private static final Logger log = LoggerFactory.getLogger(AccessResponseFuture.class);

    public static void main(String[] args) throws Exception {
            //Create thread and assign to variable (executor)
            var executor = Executors.newVirtualThreadPerTaskExecutor();

            //pass in the inialized executor to the constructor of the AggregatorService
            AggregatorService aggregator = new AggregatorService(executor);

            //Store the result from the AggregatorService getProductDTO method into a completable future variable
            List<Future<ProductDTO>> futures = IntStream.rangeClosed(1, 49)
                .mapToObj(id -> executor.submit(() -> aggregator.getProductDto(id))).toList();

            //gets the future value and maps it to ProductDTO
            var list = futures.stream()
                .map(AccessResponseFuture::toProductDTO)
                .toList();
            
        log.info("lists: {}", list);
    }

    private static ProductDTO toProductDTO(Future<ProductDTO> future) {
        try {
            return future.get();
        } catch (Exception e) {
           throw new RuntimeException();
        }
    }

   
}
