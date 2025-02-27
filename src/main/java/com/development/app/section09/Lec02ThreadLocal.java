package com.development.app.section09;

import java.time.Duration;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;

public class Lec02ThreadLocal {
      private static final Logger log = LoggerFactory.getLogger(Lec02ThreadLocal.class);
    private static final ThreadLocal<String> SESSION_TOKEN = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        Thread.ofVirtual().name("1").start(() -> processIncomingRequest());
        Thread.ofVirtual().name("2").start(() -> processIncomingRequest());

        AppUtils.sleep(Duration.ofSeconds(3));
    }

    private static void processIncomingRequest() {
        generateToken();
        controller();
    }

    private static void generateToken() {
        var token = UUID.randomUUID().toString();
        log.info("Token--{}", token);
        SESSION_TOKEN.set(token);
    }

    private static void controller() {
        log.info("controller received token--{}", SESSION_TOKEN.get());
        service();
    }

    private static void service() {
        log.info("service received token--{}", SESSION_TOKEN.get());
        Thread.ofVirtual().name("child-thread").start(() -> httpRequest());
    }

    private static void httpRequest() {
        log.info("http request made with {} in it's header", SESSION_TOKEN.get());
    }
}
