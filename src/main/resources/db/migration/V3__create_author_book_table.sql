-- db/migration/V3__create_author_book_table.sql
CREATE TABLE author_book (
                             author_id BIGINT,
                             book_id BIGINT,
                             PRIMARY KEY (author_id, book_id),
                             FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE,
                             FOREIGN KEY (book_id) REFERENCES book(id) ON DELETE CASCADE
);
