-- db/migration/V2__create_book_table.V3__create_author_book_table.sql
CREATE TABLE book (
                      id BIGSERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL
);

ALTER SEQUENCE book_id_seq RESTART WITH 11;
