package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.annotations.ThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.LongAdder;

@ThreadSafe
public class AtomicExample4 {

    //总的请求数
    private static int TOTAL_CLIENT = 1000;

    //一次线程数
    private static int CLIENT_THREAD = 50;

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(CLIENT_THREAD);
        final CountDownLatch countDownLatch = new CountDownLatch(TOTAL_CLIENT);
        /**
         * AtomicBoolean 通常用于在某些流程中只需要执行一遍的。可以用 AtomicBoolean
         */
        AtomicBoolean isHappened = new AtomicBoolean(false);
        for (int i = 0; i < TOTAL_CLIENT; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    test(isHappened);
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
        System.out.println("isHappended:" + isHappened);
    }

    private static void test(AtomicBoolean isHappened) {
        if (isHappened.compareAndSet(false, true)) {
            System.out.println("final");
        }
    }
}
