create table counter(
    "date" date,
    category varchar(50),
    cid varchar(50),
    value bigint,
    PRIMARY KEY ("date", category, cid)
);