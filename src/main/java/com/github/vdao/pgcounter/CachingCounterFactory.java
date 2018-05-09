package com.github.vdao.pgcounter;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class CachingCounterFactory implements Closeable {

    private final ConcurrentMap<CounterId, LocalCachingCounter> writableCounterRegistry;

    private final CounterStorage counterStorage;

    private final ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public CachingCounterFactory(CounterStorage counterStorage,
                                 long scheduletDumpInterval,
                                 TimeUnit timeUnit) {
        this.counterStorage = counterStorage;
        this.writableCounterRegistry = new ConcurrentHashMap<>();

        this.scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(this::dumpCounters,
                scheduletDumpInterval, scheduletDumpInterval, timeUnit);
    }

    public void dumpCounters() {
        Map<CounterId, Long> batch = writableCounterRegistry.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, v -> v.getValue().sumAndReset()));

        counterStorage.batchIncrement(batch);
    }

    public Counter getWritableCounter(CounterId counterId) {
        return writableCounterRegistry.computeIfAbsent(counterId, k -> new LocalCachingCounter());
    }

    private void releaseCounter(CounterId counterId, Counter counterInstance) {
        writableCounterRegistry.remove(counterId, counterInstance);
    }

    @Override
    public void close() throws IOException {
        scheduledThreadPoolExecutor.shutdown();
        dumpCounters();
    }

    private class LocalCachingCounter implements Counter {

        private final AtomicLong counter;

        LocalCachingCounter() {
            this.counter = new AtomicLong();
        }

        @Override
        public void increment() {
            counter.incrementAndGet();
        }

        @Override
        public void increment(long n) {
            counter.accumulateAndGet(n, (a, b) -> a + b);
        }

        @Override
        public void decrement() {
            counter.decrementAndGet();
        }

        @Override
        public void decrement(long n) {
            counter.accumulateAndGet(-n, (a, b) -> a + b);
        }

        public long sumAndReset() {
            return counter.getAndSet(0L);
        }
    }
}
