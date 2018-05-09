package com.github.vdao.pgcounter.sample;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.StampedLock;

public class CachingCounterAtomic {

    private volatile boolean isClosed = false;

    private final AtomicLong counter;

    private final StampedLock stampedLock;

    public CachingCounterAtomic() {
        this.counter = new AtomicLong();
        this.stampedLock = new StampedLock();
    }

    public void increment() {
        if (isClosed) {
            throw new IllegalStateException("The counter is closed");
        }

        long stamp = stampedLock.readLock();
        try {
            counter.incrementAndGet();
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    public long sumAndReset() {
        long stamp = stampedLock.writeLock();
        try {
            return counter.getAndSet(0L);
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }
}
