package com.mmall.concurrency.disruptor.heigh;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        int ringBufferSize = 1024 * 1024;
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        ExecutorService threadPool = Executors.newFixedThreadPool(4);

        CountDownLatch countDownLatch = new CountDownLatch(1);

        //创建 discuptor 实例对象
        Disruptor<Trade> tradeDisruptor = new Disruptor<>(new EventFactory<Trade>() {
            @Override
            public Trade newInstance() {
                return new Trade();
            }
        }, ringBufferSize, executorService, ProducerType.SINGLE, new BusySpinWaitStrategy());

        //添加消费者监听(串行)
        tradeDisruptor.handleEventsWith(new EventHandler<Trade>() {
            @Override
            public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
                System.out.println(event.getPrice());
            }
        }).handleEventsWith(new EventHandler<Trade>() {
            @Override
            public void onEvent(Trade event, long sequence, boolean endOfBatch) throws Exception {
                Thread.sleep(2000);
                System.out.println(event.getPrice());
            }
        });

        //开启 disruptor
        tradeDisruptor.start();

        //生产消息
        threadPool.submit(() -> {
            tradeDisruptor.publishEvent(new EventTraslator());
            countDownLatch.countDown();
        });
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            threadPool.shutdown();
            tradeDisruptor.shutdown();
        }

    }

    private static class EventTraslator implements EventTranslator<Trade> {

        @Override
        public void translateTo(Trade event, long sequence) {
            event.setPrice(BigDecimal.ONE);
        }
    }
}
