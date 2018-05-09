package com.github.vdao.pgcounter.sample;

import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

public class CachingCounterRWLock {

    private volatile boolean isClosed = false;

    private final LongAdder counter;

    private final ReadWriteLock stampedLock;

    public CachingCounterRWLock() {
        this.counter = new LongAdder();
        this.stampedLock = new ReentrantReadWriteLock();
    }

    public void increment() {
        if (isClosed) {
            throw new IllegalStateException("The counter is closed");
        }

        Lock lock = stampedLock.readLock();
        lock.lock();
        try {
            counter.increment();
        } finally {
            lock.unlock();
        }
    }

    public long sumAndReset() {
        Lock lock = stampedLock.writeLock();
        lock.lock();
        try {
            return counter.sumThenReset();
        } finally {
            lock.unlock();
        }
    }
}
