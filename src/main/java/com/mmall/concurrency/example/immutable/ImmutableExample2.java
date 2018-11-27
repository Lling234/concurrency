package com.mmall.concurrency.example.immutable;

import com.google.common.collect.ImmutableList;
import com.mmall.concurrency.annotations.ThreadSafe;

/**
 * 不可变对象的实现方式2
 */
@ThreadSafe
public class ImmutableExample2 {
    private static final ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);

    public static void main(String[] args) {
        list.add(123);
    }

}
