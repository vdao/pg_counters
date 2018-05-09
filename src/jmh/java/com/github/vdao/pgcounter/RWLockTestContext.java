package com.github.vdao.pgcounter;

import com.github.vdao.pgcounter.sample.CachingCounterRWLock;
import org.openjdk.jmh.annotations.*;

@State(Scope.Group)
@Threads(20)
public class RWLockTestContext {

    private CachingCounterRWLock counter;

    @Setup(Level.Iteration)
    public void prepare() {
        counter = new CachingCounterRWLock();
    }

    public CachingCounterRWLock getCounter() {
        return counter;
    }
}
