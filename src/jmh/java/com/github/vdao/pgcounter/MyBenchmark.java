package com.github.vdao.pgcounter;

import org.openjdk.jmh.annotations.*;

@BenchmarkMode(Mode.Throughput)
@Warmup(iterations = 10)
@Measurement(iterations = 10)
public class MyBenchmark {
//
//    @Benchmark()
//    @Group("prod_more_readers")
//    @GroupThreads(19)
//    public void prod_more_readers_increment(StampedLockTestContext context) {
//        context.getCounter().increment();
//    }
//
//    @Benchmark()
//    @Group("prod_more_readers")
//    @GroupThreads(1)
//    public void prod_more_readers_write(StampedLockTestContext context) {
//        context.getCounter().sumAndReset();
//    }
//
//    @Benchmark()
//    @Group("prod_no_writes")
//    @GroupThreads(20)
//    public void prod_no_writes_increment(StampedLockTestContext context) {
//        context.getCounter().increment();
//    }
//
//    @Benchmark()
//    @Group("no_check_more_readers")
//    @GroupThreads(19)
//    public void no_check_more_readers_increment(NoCheckTestContext context) {
//        context.getCounter().increment();
//    }
//
//    @Benchmark()
//    @Group("no_check_more_readers")
//    @GroupThreads(1)
//    public void no_check_more_readers_write(NoCheckTestContext context) {
//        context.getCounter().sumAndReset();
//    }
//
//    @Benchmark()
//    @Group("no_check_no_writes")
//    @GroupThreads(20)
//    public void no_check_no_writes_increment(NoCheckTestContext context) {
//        context.getCounter().increment();
//    }
//
//    @Benchmark()
//    @Group("rw_lock_more_readers")
//    @GroupThreads(19)
//    public void rw_lock_more_readers_increment(RWLockTestContext context) {
//        context.getCounter().increment();
//    }
//
//    @Benchmark()
//    @Group("rw_lock_more_readers")
//    @GroupThreads(1)
//    public void rw_lock_more_readers_write(RWLockTestContext context) {
//        context.getCounter().sumAndReset();
//    }
//
//    @Benchmark()
//    @Group("synchronized_no_writes")
//    @GroupThreads(20)
//    public void synchronized_no_writes_increment(SynchronizedTestContext context) {
//        context.getCounter().increment();
//    }
//
//    @Benchmark()
//    @Group("atomic_more_readers")
//    @GroupThreads(19)
//    public void atomic_more_readers_increment(AtomicTestContext context) {
//        context.getCounter().increment();
//    }
//
//    @Benchmark()
//    @Group("atomic_more_readers")
//    @GroupThreads(1)
//    public void atomic_more_readers_write(AtomicTestContext context) {
//        context.getCounter().sumAndReset();
//    }
//
//    @Benchmark()
//    @Group("atomic_no_writes")
//    @GroupThreads(20)
//    public void atomic_no_writes_increment(AtomicTestContext context) {
//        context.getCounter().increment();
//    }
//
//    @Benchmark()
//    @Group("synchronized_more_readers")
//    @GroupThreads(19)
//    public void synchronized_more_readers_increment(SynchronizedTestContext context) {
//        context.getCounter().increment();
//    }
//
//    @Benchmark()
//    @Group("synchronized_more_readers")
//    @GroupThreads(1)
//    public void synchronized_more_readers_write(SynchronizedTestContext context) {
//        context.getCounter().sumAndReset();
//    }
//
//    @Benchmark()
//    @Group("synchronized_nw_more_readers")
//    @GroupThreads(19)
//    public void synchronized_nw_more_readers_increment(SynchronizedNoVolatileTestContext context) {
//        context.getCounter().increment();
//    }
//
//    @Benchmark()
//    @Group("synchronized_nw_more_readers")
//    @GroupThreads(1)
//    public void synchronized_nw_more_readers_write(SynchronizedNoVolatileTestContext context) {
//        context.getCounter().sumAndReset();
//    }

    @Benchmark()
    @Group("synchronized_nw_nc_more_readers")
    @GroupThreads(19)
    public void synchronized_nw_nc_more_readers_increment(SynchronizedNoVolatileNoCheckTestContext context) {
        context.getCounter().increment();
    }

    @Benchmark()
    @Group("synchronized_nw_nc_more_readers")
    @GroupThreads(1)
    public void synchronized_nw_nc_more_readers_write(SynchronizedNoVolatileNoCheckTestContext context) {
        context.getCounter().sumAndReset();
    }

    @Benchmark()
    @Group("synchronized_nw_nc_atomic_more_readers")
    @GroupThreads(19)
    public void synchronized_nw_nc_atomic_more_readers_increment(SynchronizedNoVolatileNoCheckAtomicTestContext context) {
        context.getCounter().increment();
    }

    @Benchmark()
    @Group("synchronized_nw_nc_atomic_more_readers")
    @GroupThreads(1)
    public void synchronized_nw_nc_atomic_more_readers_write(SynchronizedNoVolatileNoCheckAtomicTestContext context) {
        context.getCounter().sumAndReset();
    }
}
