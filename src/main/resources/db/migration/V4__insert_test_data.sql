-- db/migration/V3__create_author_book_table.sql

-- Существующие данные
INSERT INTO author (id, name) VALUES (1, 'John Doe');
INSERT INTO author (id, name) VALUES (2, 'Jane Smith');
INSERT INTO author (id, name) VALUES (3, 'Bob Johnson');

INSERT INTO book (id, title) VALUES (1, 'The Great Gatsby');
INSERT INTO book (id, title) VALUES (2, 'To Kill a Mockingbird');
INSERT INTO book (id, title) VALUES (3, '1984');
INSERT INTO book (id, title) VALUES (4, 'Brave New World');
INSERT INTO book (id, title) VALUES (5, 'The Catcher in the Rye');
INSERT INTO book (id, title) VALUES (6, 'The Hobbit');
INSERT INTO book (id, title) VALUES (7, 'Fahrenheit 451');
INSERT INTO book (id, title) VALUES (8, 'The Lord of the Rings');
INSERT INTO book (id, title) VALUES (9, 'To Kill a Mockingbird');
INSERT INTO book (id, title) VALUES (10, 'Animal Farm');
INSERT INTO author (id, name) VALUES (4, 'Mary Johnson');
INSERT INTO author (id, name) VALUES (5, 'Michael Brown');

INSERT INTO author_book (author_id, book_id) VALUES (1, 1);
INSERT INTO author_book (author_id, book_id) VALUES (1, 2);
INSERT INTO author_book (author_id, book_id) VALUES (2, 3);
INSERT INTO author_book (author_id, book_id) VALUES (2, 4);
INSERT INTO author_book (author_id, book_id) VALUES (1, 5);
INSERT INTO author_book (author_id, book_id) VALUES (3, 6);
INSERT INTO author_book (author_id, book_id) VALUES (2, 7);
INSERT INTO author_book (author_id, book_id) VALUES (3, 8);
INSERT INTO author_book (author_id, book_id) VALUES (1, 9);
INSERT INTO author_book (author_id, book_id) VALUES (2, 10);

INSERT INTO author_book (author_id, book_id) VALUES (4, 1);
INSERT INTO author_book (author_id, book_id) VALUES (5, 1);

INSERT INTO author_book (author_id, book_id) VALUES (4, 2);
INSERT INTO author_book (author_id, book_id) VALUES (5, 2);

INSERT INTO author_book (author_id, book_id) VALUES (3, 4);
INSERT INTO author_book (author_id, book_id) VALUES (4, 4);


