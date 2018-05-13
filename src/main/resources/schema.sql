-- Table is growing constantly, counters are updated by stored procedure. No indices.
-- Inserts are non blocking, delete performs in single thread.
CREATE TABLE counter_delta (
    id bigserial not null,
    id1 varchar(30) not null,
    id2 varchar(30) not null,
    id3 varchar(30) not null,
    value bigint
);

-- All records from counter_delta are sequentially merged into the 'counter' table.
CREATE TABLE counter (
    id1 varchar(30) not null,
    id2 varchar(30) not null,
    id3 varchar(30) not null,
    value bigint,
    PRIMARY KEY (id1, id2, id3)
);

-- At fixed rate any counter value stored into the counter_history table to enable
-- fast calculation of counter delta at any specific time period.
CREATE TABLE counter_history (
    date date not null,
    id1 varchar(30) not null,
    id2 varchar(30) not null,
    id3 varchar(30) not null,
    value bigint,
    PRIMARY KEY (date, id1, id2, id3)
);

-- Alternative to the upsert query:
--
-- insert into counter_history(date, id1, id2, id3, value)
--     select now(), id1, id2, id3, value from counter order by id1, id2, id3
--     on conflict (date, id1, id2, id3) do update set value=counter_history.value+excluded.value;
--
-- Also, deletes processed rows.
--
CREATE OR REPLACE FUNCTION merge_counters() RETURNS VOID AS $$
DECLARE
    del_counters CURSOR FOR SELECT id1, id2, id3, value from counter_delta order by id1, id2, id3 FOR UPDATE;
	counter_rec counter_delta%ROWTYPE;
BEGIN
    OPEN del_counters;
	LOOP
	    FETCH del_counters INTO counter_rec;
		EXIT WHEN NOT FOUND;

		insert into counter(id1, id2, id3, value)
    	values(counter_rec.id1, counter_rec.id2, counter_rec.id3, counter_rec.value)
    	on conflict (id1, id2, id3) do update set value=counter.value+excluded.value;

		DELETE FROM counter_delta WHERE CURRENT OF del_counters;
	END LOOP;
	CLOSE del_counters;
END;
$$ LANGUAGE plpgsql;

-- Alternative to the upsert query:
--
-- insert into counter_history(date, id1, id2, id3, value)
--     select now(), id1, id2, id3, value from counter order by id1, id2, id3
--     on conflict (date, id1, id2, id3) do update set value=excluded.value;
--
--
CREATE OR REPLACE FUNCTION update_counter_history() RETURNS VOID AS $$
DECLARE
    del_counters CURSOR FOR SELECT id1, id2, id3, value from counter order by id1, id2, id3 FOR UPDATE;
	counter_rec counter%ROWTYPE;
BEGIN
    OPEN del_counters;
	LOOP
	    FETCH del_counters INTO counter_rec;
		EXIT WHEN NOT FOUND;

		insert into counter_history(date, id1, id2, id3, value)
    	values(now(), counter_rec.id1, counter_rec.id2, counter_rec.id3, counter_rec.value)
    	on conflict (date, id1, id2, id3) do update set value=excluded.value;
	END LOOP;
	CLOSE del_counters;
END;
$$ LANGUAGE plpgsql;