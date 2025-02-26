package com.development.app.sandbox;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 *  - A simple example of using CompletableFuture's supplyAsync() to return two records. 
 *  - Once the objects are returned, I combine them using the thenCombine() method to return one, and only one, object of the combined two
 *      - the first parameter is the other object you want to combine it with
 *      - the second parameter is the function to determine which object I want to return. For the best deal, I choise the lesser fair of the two objects
 *  - Chaining the thenApply() method takes the result of the thenCombine()
 *      - I take the result of the lesser fair object and create a new Airline record with those values from the result Airline record
 *  - Don't forget to call .join()      
 */
public class AirlineRunner {
    private static final Logger log = LoggerFactory.getLogger(AirlineRunner.class);
    private static FecthData fetchData;
    record Airline(String airline, int fair) {}

    public static void main(String[] args) {
        fetchData = new FecthData();
        var delta = getDeltaAirlineRecord();
        var frontier = getFrontierAirlineRecord();
        var bestDeal = delta.thenCombine(frontier, (a,b) -> a.fair < b.fair ? a : b)
            .thenApply(result -> new Airline(result.airline(), result.fair)).join();
        var worstDeal = delta.thenCombine(frontier,(a,b) -> a.fair < b.fair ? b : a)
        .thenApply(result -> new Airline(result.airline(), result.fair())).join();
        log.info("The airline with the best deal is--{}\nThe other airline's deal is--{}", bestDeal, worstDeal);
    }

    private static CompletableFuture<Airline> getDeltaAirlineRecord() {
        return CompletableFuture.supplyAsync(() -> {
            return new Airline("Delta",fetchData.fetchRandomAirfare());
        });
    }
    private static CompletableFuture<Airline> getFrontierAirlineRecord() {
        return CompletableFuture.supplyAsync(() -> {
            return new Airline("Frontier",fetchData.fetchRandomAirfare());
        });
    }
}
