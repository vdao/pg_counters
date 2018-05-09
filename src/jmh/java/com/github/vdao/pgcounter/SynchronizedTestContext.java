package com.github.vdao.pgcounter;

import com.github.vdao.pgcounter.sample.CachingCounterSynchronized;
import org.openjdk.jmh.annotations.*;

@State(Scope.Group)
@Threads(20)
public class SynchronizedTestContext {

    private CachingCounterSynchronized counter;

    @Setup(Level.Iteration)
    public void prepare() {
        counter = new CachingCounterSynchronized();
    }

    public CachingCounterSynchronized getCounter() {
        return counter;
    }
}
