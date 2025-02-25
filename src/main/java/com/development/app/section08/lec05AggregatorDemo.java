package com.development.app.section08;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.app.section08.aggregator.AggregatorService;

public class lec05AggregatorDemo {
    private static final Logger log = LoggerFactory.getLogger(lec05AggregatorDemo.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var executor = Executors.newVirtualThreadPerTaskExecutor();
        var aggregator = new AggregatorService(executor);
        log.info("product={}",aggregator.getProductDto(52) );
    }
}
