package com.mmall.concurrency.disruptor.multi;

import com.lmax.disruptor.RingBuffer;

public class Producer {
    private RingBuffer<Order> ringBuffer;

    public Producer(RingBuffer ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void senData(String uuid) {
        long sequence = ringBuffer.next();
        try {
            Order order = ringBuffer.get(sequence);
            order.setName(uuid);
        } finally {
            ringBuffer.publish(sequence);
        }


    }
}
