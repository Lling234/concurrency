package com.mmall.concurrency.disruptor.multi;

import com.lmax.disruptor.ExceptionHandler;

public class OrderExceptionHandler implements ExceptionHandler {
    @Override
    public void handleEventException(Throwable ex, long sequence, Object event) {
    }

    @Override
    public void handleOnStartException(Throwable ex) {
    }

    @Override
    public void handleOnShutdownException(Throwable ex) {
    }
}
