package com.github.vdao.pgcounter.sample;

import java.util.concurrent.atomic.LongAdder;

public class CachingCounterSynchronizedNoVolatileNoCheck {

    private final LongAdder counter;

    public CachingCounterSynchronizedNoVolatileNoCheck() {
        this.counter = new LongAdder();
    }

    public void increment() {
        counter.increment();
    }

    public long sumAndReset() {
        return counter.sumThenReset();
    }
}
