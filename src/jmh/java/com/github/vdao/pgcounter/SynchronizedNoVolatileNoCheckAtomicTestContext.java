package com.github.vdao.pgcounter;

import com.github.vdao.pgcounter.sample.CachingCounterSynchronizedNoVolatileNoCheck;
import com.github.vdao.pgcounter.sample.CachingCounterSynchronizedNoVolatileNoCheckAtomic;
import org.openjdk.jmh.annotations.*;

@State(Scope.Group)
@Threads(20)
public class SynchronizedNoVolatileNoCheckAtomicTestContext {

    private CachingCounterSynchronizedNoVolatileNoCheckAtomic counter;

    @Setup(Level.Iteration)
    public void prepare() {
        counter = new CachingCounterSynchronizedNoVolatileNoCheckAtomic();
    }

    public CachingCounterSynchronizedNoVolatileNoCheckAtomic getCounter() {
        return counter;
    }
}
