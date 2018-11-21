package com.mmall.concurrency.example.atomic;

import com.mmall.concurrency.annotations.ThreadSafe;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

@ThreadSafe
public class AtomicExample3 {

    /**
     * 相对于 AtomicInteger 或者 AtomicLong ，高并发情况下，效率更高点，前者通过死循环去判断当前值和底层值是否相当，来保证数据一致性
     */
    public static LongAdder count = new LongAdder();

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
        count.increment();
    }
}
