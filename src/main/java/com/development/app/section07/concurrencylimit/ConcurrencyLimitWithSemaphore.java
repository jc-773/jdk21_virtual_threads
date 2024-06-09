package com.development.app.section07.concurrencylimit;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.app.section07.Client;

public class ConcurrencyLimitWithSemaphore {
    
    private static final Logger log =  LoggerFactory.getLogger(ConcurrencyLimitWithSemaphore.class);

    public static void main(String[] args) throws Exception {
        var limiter = new ConcurrencyLimiter(Executors.newVirtualThreadPerTaskExecutor(), 3);
        execute(limiter, 20);
    }

    private static void execute(ConcurrencyLimiter limiter, int count) throws Exception {
        try (limiter) {
            for (int index = 0; index < count; index++) {
                int j = index;
                limiter.submit(() -> getProduct(j));
            }
            log.info("Submitted");
        } 
    }

    private static String getProduct(int id) {
        var product = Client.getProduct(id);
        log.info("{} => {}", id, product);
        return product;
    }
}
