package com.github.vdao.pgcounter.sample;

import java.util.concurrent.atomic.AtomicLong;

public class CachingCounterSynchronizedNoVolatileNoCheckAtomic {

    private final AtomicLong counter;

    public CachingCounterSynchronizedNoVolatileNoCheckAtomic() {
        this.counter = new AtomicLong();
    }

    public void increment() {
        counter.incrementAndGet();
    }

    public long sumAndReset() {
        return counter.getAndSet(0);
    }
}
