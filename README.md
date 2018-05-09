# Results

| Description                                                                     | Counter type  | parallel increments | parallel resets | Synchronization | Score                              |
|---------------------------------------------------------------------------------|---------------|---------------------|-----------------|-----------------|------------------------------------|
| Unsafe LongAdder based counter which may loose some counter values during reset | LongAdder     | 19                  | 1               | none            | 129142273,755 ± 1249044,088  ops/s |
| Safe LongAdder based implementation with synchronized block                     | LongAdder     | 19                  | 1               | synchronized    | 34741050,803 ± 291380,884  ops/s   |
| Safe LongAdder based implementation with new StampedLock                        | LongAdder     | 19                  | 1               | StampedLock     | 8888185,126 ± 185625,114  ops/s    |
| Safe LongAdder based implementation with ReadWriteLock                          | LongAdder     | 19                  | 1               | ReadWriteLock   | 6668714,251 ± 136189,594  ops/s    |
| Safe AtomicInteger implementation withouth additional synchronization           | AtomicInteger | 19                  | 1               | none            | 40631694,926 ± 1633429,892  ops/s  |
| Safe AtomicInteger counter with synchronized block                              | AtomicInteger | 19                  | 1               | synchronized    | 35281000,500 ± 233267,616  ops/s   |


1 500 000 insertions in 110 sec.
```
counters-# \dt+ counter
                     Список отношений
 schema |   name  |  type | owner    | Size   |
--------+---------+---------+----------+-------
 public | counter | table | postgres | 88 MB  |

```