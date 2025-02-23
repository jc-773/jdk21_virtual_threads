package com.development.app.sandbox;

import java.util.concurrent.ExecutionException;

public class Runner {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ThreadLeakExample example = new ThreadLeakExample(2);
        example.mainTask();
    }
}
