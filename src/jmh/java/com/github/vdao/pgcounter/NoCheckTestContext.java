package com.github.vdao.pgcounter;

import com.github.vdao.pgcounter.sample.CachingCounterNoCheck;
import org.openjdk.jmh.annotations.*;

@State(Scope.Group)
@Threads(20)
public class NoCheckTestContext {

    private CachingCounterNoCheck counter;

    @Setup(Level.Iteration)
    public void prepare() {
        counter = new CachingCounterNoCheck();
    }

    public CachingCounterNoCheck getCounter() {
        return counter;
    }
}
