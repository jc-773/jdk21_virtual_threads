package com.development.app.section09;

import java.time.Duration;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;

/*
 * This is just an example of creating a static ThreadLocal list of strings to emulate how a SESSION_TOKEN could be accessed from different methods and services
 * instead of being passed down via method parameter
 * 
 * 
 * Requirements for demo from scratch:
 * Create a demo that uses threadlocal to store a session token
 * The creation of the session token can be done by using UUID().getRandomUUID().toString
 * there should be a display of 1 < thread creation(s) that call a controller, that calls a service, that calls http 
 *  - this is a high level mock of working a session_token all the way up from the controller down to a the http call where it would be added as a header
 */
public class Lec01ThreadLocal {
    private static final Logger log = LoggerFactory.getLogger(Lec01ThreadLocal.class);
    private static final ThreadLocal<String> SESSION_TOKEN = new ThreadLocal<>();

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
        httpRequest();
    }

    private static void httpRequest() {
        log.info("http request made with {} in it's header", SESSION_TOKEN.get());
    }
}
