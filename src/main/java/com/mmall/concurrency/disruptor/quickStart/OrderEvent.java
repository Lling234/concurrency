package com.mmall.concurrency.disruptor.quickStart;

public class OrderEvent {
    private Long value;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
