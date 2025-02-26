package com.development.app.sandbox;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;

/*
    - This example is to test the hypothesis that using supplyAsync() with virtual threads will not be any faster than a regular blocking call because a blocking call is a blocking call
    - Using virtual threads allows us to scale the amount of requests to the blocking call. This gives the illusion of non-blocking
    - Using CompletableFutures supplyAsync() method, allows us to asynchronously kick off a task that wont hold up the main thread
            - it is important to know that your chained commands will execute and the application will not wait for the future result to be retrieved -unless you use .join() or .get()
    - I chose to use a semaphore with a resource permit max of 3, just because.
    - Thread type should be assigned in the supplyAsync parameter
            - I tried creating my own and calling executor.submit({asyncFetch()}) but the main thread would complete before the future value was able to be retrieved.
*/
public class Runner {
    private static final Logger log = LoggerFactory.getLogger(Runner.class);
    private static FecthData fecthData;
    private static Semaphore semaphore;
    private static int resultCount = 0;

    
    public static void main(String[] args) throws Exception {
        fecthData = new FecthData();
        semaphore = new Semaphore(3);
       
        log.info("time--{}", AppUtils.timer(() -> {
            CompletableFuture<String> future = aysncFetch();
            CompletableFuture<String> future2 = aysncFetch();
            CompletableFuture<String> future3 = aysncFetch();
            CompletableFuture<String> future4 = aysncFetch();
            CompletableFuture<String> future5 = aysncFetch();

            String data = future.join();
            String data2 = future2.join();
            String data3 = future3.join();
            String data4 = future4.join();
            String data5 = future5.join();
           

            log.info("result --{}, data--{}", resultCount, data);
            log.info("result2--{}, data--{}", resultCount, data2);
            log.info("result3--{}, data--{}",resultCount, data3);
            log.info("result4--{}, data--{}",resultCount, data4);
            log.info("result5--{}, data--{}",resultCount, data5);


            // CompletableFuture<String> future2 = aysncFetch();
            // String data2 = future2.join();
            // log.info("result2--{}, data--{}", resultCount, data2);

            // CompletableFuture<String> future3 = aysncFetch();
            // String data3 = future3.join();
            // log.info("result3--{}, data--{}",resultCount, data3);

            // CompletableFuture<String> future4 = aysncFetch();
            // String data4 = future4.join();
            // log.info("result4--{}, data--{}",resultCount, data4);

            // CompletableFuture<String> future5 = aysncFetch();
            // String data5 = future5.join();
            // log.info("result5--{}, data--{}",resultCount, data5);

        }));
        semaphore.release();
    }

    private static CompletableFuture<String> aysncFetch() {
        try {
            semaphore.acquire();
            resultCount++;
            return CompletableFuture
            .supplyAsync(() -> fecthData.fetchData(), Executors.newVirtualThreadPerTaskExecutor())
            .thenApply(data -> processData(data))// thenApply() only invoked after fetchData returns a result
            .whenComplete((result, ex) -> semaphore.release());
        } catch (Exception e) {
            e.printStackTrace();
        } 
        return null;
    }

    private static String sequentialFetch(ExecutorService executor) {
        String data = fecthData.fetchData();
        return processData(data);
    }

    private static String processData(String data) {
        log.info("Data processed-{}", data);
        return data + " verified";
    }
}
