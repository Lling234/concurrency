package com.mmall.concurrency.ThreadLocal;

public class RequestHolder {
    private final static ThreadLocal<Long> LONG_THREAD_LOCAL = new ThreadLocal<>();

    public static void add(Long id) {
        LONG_THREAD_LOCAL.set(id);
    }

    public static Long get() {
        return LONG_THREAD_LOCAL.get();
    }

    public static void remove() {
        LONG_THREAD_LOCAL.remove();
    }
}
