package com.github.vdao.pgcounter;

public interface Counter {

    void increment();

    void increment(long n);

    void decrement();

    void decrement(long n);
}