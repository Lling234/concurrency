package com.mmall.concurrency.disruptor.quickStart;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class OrderEventProducer {
    private RingBuffer<OrderEvent> ringBuffer;

    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void sendData(ByteBuffer byteBuffer) {

    }
}
