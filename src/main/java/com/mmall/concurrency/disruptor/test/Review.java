package com.mmall.concurrency.disruptor.test;

import java.util.concurrent.locks.LockSupport;

public class Review {
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(() -> {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += i;
            }
            LockSupport.park();
            System.out.println("求和:" + sum);
        });

        thread.start();
        Thread.sleep(2000);

        LockSupport.unpark(thread);
    }
}
