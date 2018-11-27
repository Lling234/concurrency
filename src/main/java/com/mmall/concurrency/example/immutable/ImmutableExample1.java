package com.mmall.concurrency.example.immutable;

import com.google.common.collect.Maps;
import com.mmall.concurrency.annotations.ThreadSafe;

import java.util.Collections;
import java.util.Map;

/**
 * 不可变对象的实现方式1
 */
@ThreadSafe
public class ImmutableExample1 {
    private static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 1);
        map.put(2, 2);
        map = Collections.unmodifiableMap(map);
    }

    public static void main(String[] args) {
        map.put(3, 3);
    }

}
