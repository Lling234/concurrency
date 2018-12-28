package com.mmall.concurrency.disruptor.quickStart;

public class FalseSharingTest implements Runnable {
    private final static int NUM_THREADS = 8;
    private final static long ITERATIONS = 500L * 1000L * 1000L;
    private final int arrayIndex;

    private static ValueWithPadding[] arrs = new ValueWithPadding[NUM_THREADS];

    static {
        for (int i = 0; i < arrs.length; i++) {
            arrs[i] = new ValueWithPadding();
        }
    }

    FalseSharingTest(final int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    public static void main(final String[] args) throws Exception {
        final long start = System.currentTimeMillis();
        runTest();
        System.out.println("duration = " + (System.currentTimeMillis() - start) + "ms");
    }

    private static void runTest() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new FalseSharingTest(i));
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }
    }

    public void run() {
        long i = ITERATIONS + 1;
        while (0 != --i) {
            arrs[arrayIndex].value = i;
        }
    }

    public final static class ValueWithPadding {
        protected long p1, p2, p3, p4, p5, p6, p7;
        protected volatile long value = 0L;
        protected long p9, p10, p11, p12, p13, p14;
        protected long p15;
    }

    public final static class ValueWithoutPadding {
        protected volatile long value = 0L;
    }
}
