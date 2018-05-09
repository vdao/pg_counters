package com.github.vdao.pgcounter.sample;

import java.util.concurrent.atomic.LongAdder;

public class CachingCounterSynchronizedNoVolatile {

    private boolean isClosed = false;

    private final LongAdder counter;

    public CachingCounterSynchronizedNoVolatile() {
        this.counter = new LongAdder();
    }

    public synchronized void increment() {
        if (isClosed) {
            throw new IllegalStateException("The counter is closed");
        }

        counter.increment();
    }

    public synchronized long sumAndReset() {
        return counter.sumThenReset();
    }
}
