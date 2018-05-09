package com.github.vdao.pgcounter;

import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@State(Scope.Group)
@Threads(20)
public class StampedLockTestContext {

    private LocalCachingCounter counter;

    @Setup(Level.Iteration)
    public void prepare() {
        counter = new LocalCachingCounter();
    }

    public LocalCachingCounter getCounter() {
        return counter;
    }
}
