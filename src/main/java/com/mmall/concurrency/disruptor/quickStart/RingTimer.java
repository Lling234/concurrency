package com.mmall.concurrency.disruptor.quickStart;

import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class RingTimer {
    private static int currentIndex = 0;

    public static void main(String[] args) {
        final Map[] list = new Map[30];
        for (int i = 0; i < 30; i++) {
            list[i] = new ConcurrentHashMap<Integer, Integer>();
        }

        final Map<Integer, Integer> uid2index = new ConcurrentHashMap<Integer, Integer>();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                currentIndex = currentIndex % 30;
                System.out.println("current check at " + new Date().toString() + ",index:" + currentIndex);
                Map map = list[currentIndex];
                if (map.size() > 0) {
                    for (Object key : map.keySet()) {
                        System.out.print(key + "\t");
                        uid2index.remove(key);
                    }
                    map.clear();
                    System.out.println("timeout!");
                }
                currentIndex++;
            }
        }, 0, 1000);
        System.out.println("start timer at:" + new Date().toString());

        for (int i = 0; i < 10; i++) {
            int index = (currentIndex - 1 + 30) % 30;
            uid2index.put(i, index);
            list[index].put(i, i);
            System.out.println("add uid:" + i + " to index:" + index + " at:" + new Date().toString());
            try {
                Thread.currentThread().sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        int count = Integer.MAX_VALUE / 2;
        while (count-- > 0) ;
    }
}
