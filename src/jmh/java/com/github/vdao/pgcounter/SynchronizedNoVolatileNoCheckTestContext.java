package com.github.vdao.pgcounter;

import com.github.vdao.pgcounter.sample.CachingCounterSynchronizedNoVolatileNoCheck;
import org.openjdk.jmh.annotations.*;

@State(Scope.Group)
@Threads(20)
public class SynchronizedNoVolatileNoCheckTestContext {

    private CachingCounterSynchronizedNoVolatileNoCheck counter;

    @Setup(Level.Iteration)
    public void prepare() {
        counter = new CachingCounterSynchronizedNoVolatileNoCheck();
    }

    public CachingCounterSynchronizedNoVolatileNoCheck getCounter() {
        return counter;
    }
}
