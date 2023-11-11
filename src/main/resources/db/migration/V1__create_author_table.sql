-- db/migration/V1__create_author_table.sql
CREATE TABLE author (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL
);

ALTER SEQUENCE author_id_seq RESTART WITH 4;
