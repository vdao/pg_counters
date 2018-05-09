package com.github.vdao.pgcounter.postgres;

import com.github.vdao.pgcounter.CounterId;
import com.github.vdao.pgcounter.CounterStorage;

import java.sql.*;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;

public class PostgresCounterStorage implements CounterStorage {

    private final Connection connection;

    public PostgresCounterStorage(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void batchIncrement(Map<CounterId, Long> batch) {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into counter(date, category, cid, val) values (?, ?, ?, ?)" +
                        " on conflict (date, category, cid) do update set val=counter.val+excluded.val")) {

            batch.entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getKey,
                            Comparator.comparing(v -> v.getCategory() + "_" + v.getId())))
                    .forEach(e -> {
                        try {
                            // Avoid empty value insertion
                            if (e.getValue() != 0) {
                                statement.setDate(1, new Date(System.currentTimeMillis()));
                                statement.setString(2, e.getKey().getCategory());
                                statement.setString(3, e.getKey().getId());
                                statement.setLong(4, e.getValue());
                                statement.addBatch();
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    });

            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
