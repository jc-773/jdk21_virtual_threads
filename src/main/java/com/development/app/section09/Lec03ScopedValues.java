package com.development.app.section09;

import java.time.Duration;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;


public class Lec03ScopedValues {
    private static final Logger log = LoggerFactory.getLogger(Lec03ScopedValues.class);
    private static final ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();

    public static void main(String[] args) {
        Thread.ofVirtual().name("1").start(() -> processIncomingRequest());
        Thread.ofVirtual().name("2").start(() -> processIncomingRequest());
        AppUtils.sleep(Duration.ofSeconds(2));
    }

    private static void processIncomingRequest() {
        var token = generateToken();
        
        ScopedValue.runWhere(SESSION_TOKEN, token, () -> controller());
    }

    private static String generateToken() {
        var token = UUID.randomUUID().toString();
        log.info("Token--{}", token);
        return token;
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
