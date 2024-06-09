package com.development.app.section07.concurrencylimit;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConcurrencyLimiterWithQueue {
     private static final Logger log = LoggerFactory.getLogger(ConcurrencyLimiterWithQueue.class);

    private static ExecutorService executor;
    private static Semaphore semaphore;
    private final Queue<Callable<?>> queue;

    public ConcurrencyLimiterWithQueue(ExecutorService executor, int limit) {
        this.executor = executor;
        this.semaphore = new Semaphore(limit);
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public <T> Future<T> submit(Callable <T> callable) {
        this.queue.add(callable);
       return  executor.submit(() -> executeTask());
    }

    //whoever gets the permit from semaphore will pull the task from the queue that remains the order. So the task that came first will be polled and executed 
    private <T> T executeTask() {
        try {
            semaphore.acquire();
            return (T) this.queue.poll().call();
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
