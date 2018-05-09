package com.github.vdao.pgcounter;

import java.util.Map;

public interface CounterStorage {

    void batchIncrement(Map<CounterId, Long> batch);
}
