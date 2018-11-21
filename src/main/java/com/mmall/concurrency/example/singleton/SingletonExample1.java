package com.mmall.concurrency.example.singleton;


import com.mmall.concurrency.annotations.UnRecommend;
import com.mmall.concurrency.annotations.UnThreadSafe;

/**
 * 懒汉模式
 */
@UnThreadSafe
@UnRecommend
public class SingletonExample1 {
    private SingletonExample1() {

    }

    private static SingletonExample1 singletonExample1 = null;

    public static SingletonExample1 getInstance() {
        if (singletonExample1 == null) {
            singletonExample1 = new SingletonExample1();
        }
        return singletonExample1;
    }
}
