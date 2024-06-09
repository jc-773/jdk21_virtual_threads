package com.development.app.section07.concurrencylimit;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConcurrencyLimiter implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimiter.class);

    private static ExecutorService executor;
    private static Semaphore semaphore;

    public ConcurrencyLimiter(ExecutorService executor, int limit) {
        this.executor = executor;
        this.semaphore = new Semaphore(limit);
    }

    public <T> Future<T> submit(Callable <T> callable) {
       return  executor.submit(() -> wrapCallable(callable));
    }

    private <T> T wrapCallable(Callable <T> callable) {
        try {
            semaphore.acquire();
            return callable.call();
        } catch (Exception e) {
            log.info("error {}", e);
        } finally {
            semaphore.release();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        executor.close();
    }

    
}
