package com.github.vdao.pgcounter;

import com.github.vdao.pgcounter.sample.CachingCounterAtomic;
import org.openjdk.jmh.annotations.*;

@State(Scope.Group)
@Threads(20)
public class AtomicTestContext {

    private CachingCounterAtomic counter;

    @Setup(Level.Iteration)
    public void prepare() {
        counter = new CachingCounterAtomic();
    }

    public CachingCounterAtomic  getCounter() {
        return counter;
    }
}
