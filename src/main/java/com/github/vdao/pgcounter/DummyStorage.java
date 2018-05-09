package com.github.vdao.pgcounter;

import java.util.Map;

public class DummyStorage implements CounterStorage {

    @Override
    public void batchIncrement(Map<CounterId, Long> batch) {

    }
}
