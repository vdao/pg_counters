package com.github.vdao.pgcounter.sample;

import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.StampedLock;

public class CachingCounterSynchronized {

    private volatile boolean isClosed = false;

    private final LongAdder counter;

    public CachingCounterSynchronized() {
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
