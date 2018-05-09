package com.github.vdao.pgcounter;

import com.github.vdao.pgcounter.sample.CachingCounterSynchronizedNoVolatile;
import org.openjdk.jmh.annotations.*;

@State(Scope.Group)
@Threads(20)
public class SynchronizedNoVolatileTestContext {

    private CachingCounterSynchronizedNoVolatile counter;

    @Setup(Level.Iteration)
    public void prepare() {
        counter = new CachingCounterSynchronizedNoVolatile();
    }

    public CachingCounterSynchronizedNoVolatile getCounter() {
        return counter;
    }
}
