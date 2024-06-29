package com.development.app.section07.deliverable;

import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.app.section07.Client;
import com.development.app.section07.concurrencylimit.ConcurrencyLimiter;


public class ConcurrencyUser {
     private static final Logger log = LoggerFactory.getLogger(ConcurrencyUser.class);

    

    public static void main(String[] args) throws Exception {
         var limiter = new ConcurrencyLimiter(Executors.newVirtualThreadPerTaskExecutor(), 3);
        execute(limiter, 20);
    }

    private static void execute(ConcurrencyLimiter limiter, int count) throws Exception {
        try (limiter) {
            for (int index = 0; index < count; index++) {
                int j = index;
                limiter.submit(() -> getUsername(count));
            }
            log.info("Submitted");
        } 
    }

    private static String getUsername(int id) {
        var product = Client.getProduct(id);
        log.info("{} => {}", id, product);
        return product;
    }

}
