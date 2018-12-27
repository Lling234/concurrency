package com.mmall.concurrency.disruptor.multi;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        //创建 RingBuffer 实例对象
        RingBuffer<Order> ringBuffer = RingBuffer.create(ProducerType.MULTI, new EventFactory<Order>() {
            @Override
            public Order newInstance() {
                return new Order();
            }
        }, 32 * 32, new YieldingWaitStrategy());

        //通过 RingBuffer 创建一个屏障
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();

        //创建消费者数组
        Customer[] customers = new Customer[10];
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer("C" + i);
        }

        //创建多消费者工作池
        WorkerPool<Order> workerPool = new WorkerPool<Order>(ringBuffer,
                sequenceBarrier, new OrderExceptionHandler(), customers);

        //设置多个消费者的 sequence 序号 用于单独统计消费进度，并且设置到ringbuffer中
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());

        //启动 workPool
        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));

        //创建生产者
        CountDownLatch latch = new CountDownLatch(1);

        for (int i = 0; i < 5; i++) {
            Producer producer = new Producer(ringBuffer);
            new Thread(() -> {
                try {
                    latch.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                for (int b = 0; b < 100; b++) {
                    producer.senData(UUID.randomUUID().toString());
                }
            }).start();
        }

        Thread.sleep(2000);
        System.out.println("线程创建完毕，生产者开始生产数据--------------");
        latch.countDown();

        Thread.sleep(10000);
        System.out.println("消费者1所消费的数据量为:" + customers[2].getCount());


    }
}
