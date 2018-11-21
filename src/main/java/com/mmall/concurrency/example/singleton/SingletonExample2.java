package com.mmall.concurrency.example.singleton;


import com.mmall.concurrency.annotations.UnRecommend;
import com.mmall.concurrency.annotations.UnThreadSafe;

/**
 * 饿汉模式
 */
@UnThreadSafe
@UnRecommend
public class SingletonExample2 {
    private SingletonExample2() {

    }

    private static SingletonExample2 instance = new SingletonExample2();

    public static SingletonExample2 getInstance() {
        return instance;
    }
}
