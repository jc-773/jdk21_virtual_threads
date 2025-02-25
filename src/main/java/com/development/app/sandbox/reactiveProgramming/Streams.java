package com.development.app.sandbox.reactiveProgramming;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Streams {
    public static void main(String[] args) {
        basicStreamExample();
    }

    public static Mono<List<Integer>> basicStreamExample() {
        Mono<List<Integer>> list = Flux.just(1,2,3,4,5)
            .map(n -> n * 2)
            .collectList();
            //.subscribe(n -> System.out.println(n));
            return list;
    }
}
