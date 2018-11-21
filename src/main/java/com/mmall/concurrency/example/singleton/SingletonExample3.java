package com.mmall.concurrency.example.singleton;


import com.mmall.concurrency.annotations.Recommend;
import com.mmall.concurrency.annotations.ThreadSafe;
import com.mmall.concurrency.annotations.UnRecommend;
import com.mmall.concurrency.annotations.UnThreadSafe;

/**
 * 枚举单例模式
 */
@ThreadSafe
@Recommend
public class SingletonExample3 {
    private SingletonExample3() {

    }

    public static SingletonExample3 getInstance() {
        return instance.INSTANCE.getInstance();
    }

    private enum instance {
        INSTANCE;

        private SingletonExample3 instance;

        instance() {
            instance = new SingletonExample3();

        }

        public SingletonExample3 getInstance() {
            return instance;
        }

    }
}
