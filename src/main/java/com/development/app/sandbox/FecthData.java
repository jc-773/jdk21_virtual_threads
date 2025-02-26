package com.development.app.sandbox;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.development.AppUtils;

public class FecthData {
    private static final Logger log = LoggerFactory.getLogger(FecthData.class);

    public int fetchRandomAirfare() {
        return ThreadLocalRandom.current().nextInt(50, 250);
    }

    public String fetchData() {
        try {
            AppUtils.sleep(Duration.ofSeconds(2));
            log.info("web service request complete");
        } catch (Exception e) {
            log.error("Exception: {}", e);
        } 
        return "Data";
    }
}
