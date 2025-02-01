package com.development.app.section08.funtionalIntefacePractice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FunctionalInterfaceCaller {
    private static final Logger log = LoggerFactory.getLogger(FunctionalInterfaceCaller.class);
    public static void main(String[] args) {
        FunctionalInterfaceExample executor = blueprint -> {
            log.info("Task started");
            blueprint.run();
            log.info("Task ended");
        };

        Runnable task = () -> {
            log.info("Runnable task invoked");
        };

        executor.runAsyncTask(task);
    }
}
