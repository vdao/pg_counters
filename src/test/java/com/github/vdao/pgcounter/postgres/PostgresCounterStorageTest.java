package com.github.vdao.pgcounter.postgres;

import com.github.vdao.pgcounter.CachingCounterFactory;
import com.github.vdao.pgcounter.CounterId;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class PostgresCounterStorageTest {

    @Test
    public void testIncrement() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/counters?user=postgres&password=postgres";
        Connection conn = DriverManager.getConnection(url);

        PostgresCounterStorage postgresCounterStorage = new PostgresCounterStorage(conn);
        CachingCounterFactory cachingCounterFactory = new CachingCounterFactory(postgresCounterStorage,
                15, TimeUnit.SECONDS);

        String category = "tag";

        for (int iteration = 0; iteration < 10; iteration++) {
            long start = System.currentTimeMillis();
            for (int item = 0; item < 1000; item++) {
                for (int feature = 0; feature < 100; feature++) {
                    CounterId id = new CounterId(category, item + "_" + feature);
                    cachingCounterFactory.getWritableCounter(id).increment();
                }
            }
            System.out.println("count_time=" + (System.currentTimeMillis() - start));
            start = System.currentTimeMillis();
            cachingCounterFactory.dumpCounters();
            System.out.println("insertion_time=" + (System.currentTimeMillis() - start));
        }

        cachingCounterFactory.close();
    }

    @Test
    public void testIncrementInBackground() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/counters?user=postgres&password=postgres";
        Connection conn = DriverManager.getConnection(url);

        PostgresCounterStorage postgresCounterStorage = new PostgresCounterStorage(conn);
        CachingCounterFactory cachingCounterFactory = new CachingCounterFactory(postgresCounterStorage,
                15, TimeUnit.SECONDS);

        String category = "tag";

        long start = System.currentTimeMillis();
        for (int view = 0; view < 100_000; view++) {
            for (int item = 0; item < 100; item++) {
                for (int feature = 0; feature < 500; feature++) {
                    CounterId id = new CounterId(category, item + "_" + feature);
                    cachingCounterFactory.getWritableCounter(id).increment();
                }
            }
        }
        System.out.println("count_time=" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        cachingCounterFactory.close();
        System.out.println("insertion_time=" + (System.currentTimeMillis() - start));
    }

    @Test
    public void test_large_interval() throws ClassNotFoundException, SQLException, IOException {
        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://localhost/counters?user=postgres&password=postgres";
        Connection conn = DriverManager.getConnection(url);

        PostgresCounterStorage postgresCounterStorage = new PostgresCounterStorage(conn);
        CachingCounterFactory cachingCounterFactory = new CachingCounterFactory(postgresCounterStorage,
                15, TimeUnit.SECONDS);

        String category = "tag";
        long start = System.currentTimeMillis();
        for (int day = 0; day < 30; day++) {
            for (int item = 0; item < 100; item++) {
                for (int feature = 0; feature < 500; feature++) {
                    CounterId id = new CounterId(category + "_" + day, item + "_" + feature);
                    cachingCounterFactory.getWritableCounter(id).increment();
                }
            }
        }
        System.out.println("count_time=" + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        cachingCounterFactory.close();
        System.out.println("insertion_time=" + (System.currentTimeMillis() - start));
    }
}