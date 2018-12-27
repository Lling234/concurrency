package com.mmall.concurrency.disruptor.multi;

import com.lmax.disruptor.WorkHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class Customer implements WorkHandler<Order> {

    private String customerId;

    private static AtomicInteger count = new AtomicInteger(0);

    Customer(String customerId) {
        this.customerId = customerId;
    }

    @Override
    public void onEvent(Order event) throws Exception {
        System.out.println("当前消费者:" + customerId + "name:" + event.getName());
        count.incrementAndGet();
    }

    public int getCount() {
        return count.get();
    }
}
