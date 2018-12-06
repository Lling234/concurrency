package com.mmall.concurrency.example.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class futureExample1 {
    static class myCallable implements Callable<String> {
        @Override
        public String call() throws Exception {
            System.out.println("join thread!");
            Thread.sleep(5000);
            return "has done";
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> stringFuture = executorService.submit(new myCallable());
        System.out.println("main method execute!");
        Thread.sleep(1000);
        System.out.println("result:" + stringFuture.get());
    }
}
