package com.mmall.concurrency.disruptor.quickStart;

public class CacheLineTest {
    public static long[][] arr;//一个long占8字节,缓存行一般为64字节,linux下可以通过 cat /proc/cpuinfo 查看cache_alignment的大小来确定缓存行大小

    private static final int SIZE = 4096 * 4096;

    public static void main(String[] args) {
        arr = new long[SIZE][];//可以设置更大点，更能看出效果
        for (int i = 0; i < SIZE; i++) {
            arr[i] = new long[8];
            for (int j = 0; j < 8; j++) {
                arr[i][j] = i * j + 1;
            }
        }
        long sum = 0L;
        long start = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i += 1) {
            for (int j = 0; j < 8; j++) {//这里相当于从cacheline中访问
                sum += arr[i][j];
            }
        }
        System.out.println("sum=" + sum + ",time cost with cache line access:" + (System.currentTimeMillis() - start) + "ms");

        sum = 0L;
        start = System.currentTimeMillis();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < SIZE; j++) {
                sum += arr[j][i];
            }
        }
        System.out.println("sum=" + sum + ",time cost without cache line access:" + (System.currentTimeMillis() - start) + "ms");
    }
}
