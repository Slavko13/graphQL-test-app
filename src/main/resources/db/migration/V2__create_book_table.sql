-- db/migration/V2__create_book_table.sql
CREATE TABLE book (
                      id BIGSERIAL PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      author_id BIGINT NOT NULL,
                      FOREIGN KEY (author_id) REFERENCES author(id)
);

ALTER SEQUENCE book_id_seq RESTART WITH 11;
