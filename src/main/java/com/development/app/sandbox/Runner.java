package com.development.app.sandbox;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;

public class Runner {
    private static final Logger log = LoggerFactory.getLogger(Runner.class);

    private static ProcessFetch process;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        process = new ProcessFetch(3);
        //log.info("Time for blocking sequential task: {}", AppUtils.timer(()  -> executeBlockingTask()));
        
        //virtual threads are daemon threads, so when the main thread is finished executing, the application will end. Regardless if supplyAsync has a value or not.
        //You must use the .join() method to hold the main thread to wait for the result of supplyAsyncronousFetch()
        log.info("Time for non-blocking concurrent task: {}", AppUtils.timer(()  -> log.info("Result: {}",supplyAsynchronousFetch().join())));
    }

    private static CompletableFuture<String> supplyAsynchronousFetch() {
        var future = CompletableFuture.supplyAsync(() -> { //each task within this supplyAsync() pipeline executes asynchronously
            String data = process.fetchData();
            return process(data);
        }, Executors.newVirtualThreadPerTaskExecutor());
        return future;
    }

    private static void executeSequentialFetch() {
        String data = process.fetchData();
        String processData = process(data);
        log.info(processData);
    }

    private static String process(String data) {
        return data + " squared";
    }
}
