package com.github.vdao.pgcounter;

import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.StampedLock;

public class LocalCachingCounter {

    private volatile boolean isClosed = false;

    private final LongAdder counter;

    private final StampedLock stampedLock;

    LocalCachingCounter() {
        this.counter = new LongAdder();
        this.stampedLock = new StampedLock();
    }

    public void increment() {
        if (isClosed) {
            throw new IllegalStateException("The counter is closed");
        }

        long stamp = stampedLock.readLock();
        try {
            counter.increment();
        } finally {
            stampedLock.unlockRead(stamp);
        }
    }

    public long sumAndReset() {
        long stamp = stampedLock.writeLock();
        try {
            return counter.sumThenReset();
        } finally {
            stampedLock.unlockWrite(stamp);
        }
    }
}
