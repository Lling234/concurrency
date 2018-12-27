package com.mmall.concurrency.disruptor.quickStart;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisruptorMain {
    public static void main(String[] args) {
        int ringBufferSize = 1024 * 1024;

        /**
         *  OrderEventFactory 消息工厂
         *  ringBufferSize 容器长度
         *  executorService 现场池(建议自定义线程池)
         *  ProducerType 单生产者 还是多生产者
         *  BlockingWaitStrategy 等待策略
         */
        //创建实例
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        Disruptor<OrderEvent> disruptor = new Disruptor<>(new OrderEventFactory(),
                ringBufferSize, executorService, ProducerType.SINGLE, new BlockingWaitStrategy());

        //添加消费者监听
        disruptor.handleEventsWith(new OrderEventHandler());

        //启动 disruptor
        disruptor.start();

        //获取实际存储数据的容器
        RingBuffer<OrderEvent> ringBuffer = disruptor.getRingBuffer();

        for (int i = 0; i < 100; i++) {
            long sequence = ringBuffer.next();
            try {
                OrderEvent orderEvent = ringBuffer.get(sequence);
                orderEvent.setValue((long) i);
            } finally {
                ringBuffer.publish(sequence);
            }
        }

        disruptor.shutdown();
        executorService.shutdown();
    }
}
