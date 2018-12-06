package com.mmall.concurrency.example.commonUnsafe;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class StringExample1 {

    //总的请求数
    public static int totalClient = 1000;

    //一次线程数
    public static int clientThread = 50;

    public static final StringBuilder STRING_BUFFER = new StringBuilder();

    public static final Set set = new ConcurrentSkipListSet<Long>();

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(clientThread);
        final CountDownLatch countDownLatch = new CountDownLatch(totalClient);
        for (int i = 0; i < totalClient; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    append(finalI);
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
//        System.out.println("count:" + STRING_BUFFER.length());
        System.out.println("count:" + set.size());
    }

    private static void append(int i) {
        STRING_BUFFER.append("1");
        set.add(i);
    }
}
