package com.github.vdao.pgcounter.sample;

import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.StampedLock;

public class CachingCounterNoCheck {

    private final LongAdder counter;

    private final StampedLock stampedLock;

    public CachingCounterNoCheck() {
        this.counter = new LongAdder();
        this.stampedLock = new StampedLock();
    }

    public void increment() {
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
