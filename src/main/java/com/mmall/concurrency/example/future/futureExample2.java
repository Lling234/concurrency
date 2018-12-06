package com.mmall.concurrency.example.future;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class futureExample2 {

    public static void main(String[] args) throws Exception {
        FutureTask futureTask = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("join thread!");
                Thread.sleep(5000);
                return "has done";
            }
        });
        new Thread(futureTask).start();
        System.out.println("main method execute!");
        Thread.sleep(1000);
        System.out.println("result:" + futureTask.get());
    }
}
