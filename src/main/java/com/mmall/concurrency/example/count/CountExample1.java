package com.mmall.concurrency.example.count;

import com.mmall.concurrency.annotations.UnThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@UnThreadSafe
public class CountExample1 {

    public static int count = 0;

    //总的请求数
    public static int totalClient = 1000;

    //一次线程数
    public static int clientThread = 50;

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(clientThread);
        final CountDownLatch countDownLatch = new CountDownLatch(totalClient);
        for (int i = 0; i < totalClient; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("count:" + count);
    }

    private static void add() {
        count++;
    }
}
