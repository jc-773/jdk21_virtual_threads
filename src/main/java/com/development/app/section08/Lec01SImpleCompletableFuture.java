package com.development.app.section08;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;


/**
 * 
 * This class is an example of how using completable future won't always get you the future result within the lifespan of the application
 * Because virtual threads are daemon threads, the thread running the main method may end if the future result is not retrieved in time
 * 
 * This only happens if we are retrieving the future value in a non-blocking way (i.e. using thenAccept())
 * 
 * The application will however wait for the future value to be retreived if we use a blocking method like .get() or .join()
 * 
 */
public class Lec01SImpleCompletableFuture {
    private static final Logger log = LoggerFactory.getLogger(Lec01SImpleCompletableFuture.class);

    public static void main(String[] args) {
      log.info("main started ");
      var completableFuture = slowTask();
      completableFuture.thenAccept(v -> log.info("value {}", v));
      //log.info("CompletableFuture value: {}", completableFuture.join());
      AppUtils.sleep(Duration.ofSeconds(2));
      log.info("main ended ");
    }

    private static CompletableFuture<String> fastTask() {
        log.info("fastTask started ");
        var completableFuture = new CompletableFuture<String>();
        completableFuture.complete("Hello Completable");
        log.info("fastTask ended ");
        return completableFuture;
    }

    private static CompletableFuture<String> slowTask() {
        log.info("fastTask started ");
        var completableFuture = new CompletableFuture<String>();
        Thread.ofVirtual().start(() -> {
            AppUtils.sleep(Duration.ofSeconds(1));
            completableFuture.complete("Hello Completable");
        });
        log.info("fastTask ended ");
        return completableFuture;
    }
}
