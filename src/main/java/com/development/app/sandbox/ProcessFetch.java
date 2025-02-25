package com.development.app.sandbox;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;

public class ProcessFetch {
    private static final Logger log = LoggerFactory.getLogger(Process.class);

    private static Semaphore semaphore;

    public ProcessFetch(int n) {
         semaphore = new Semaphore(n);
    }
    
    public String fetchData() {
        try {
            AppUtils.sleep(Duration.ofSeconds(2));
        } catch (Exception e) {
            log.error("Exception: {}", e);
        }
        return "Data";
    }

    // public String fetchDataVirtualThreads() {
    //     try {
    //         semaphore.acquire();
    //     } catch (InterruptedException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }
    //     try(var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    //         executor.submit(() -> {
    //             AppUtils.sleep(Duration.ofSeconds(2));
    //         });
    //     }
    //     semaphore.release();
    //     return "Data";
    // }
}
