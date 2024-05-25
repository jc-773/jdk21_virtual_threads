package com.development.app.section07;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {

    // Logger
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    // product URL
    public static final String PRODUCT_URL = "http://localhost:8082/product?id=%d";

    // rating URL
    public static final String RATING_URL = "http://localhost:8082/rating?id=%d";

    public static String getProduct(int id) {
        return callExternalService(PRODUCT_URL.formatted(id));
    }

    public static String getRating(int id) {
        return callExternalService(RATING_URL.formatted(id));
    }

    private static String callExternalService(String url) {
        // log
        log.info("calling {}", url);

        // open up a stream
        try (var stream = URI.create(url).toURL().openStream()) { // stream should be closed in try-with-resources
            return new String(stream.readAllBytes());
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
