package ru.test.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.app.model.Author;
import ru.test.app.model.Book;

import java.util.List;


/**
 * Repository interface for the Book entity.
 * Author: Viacheslav Petrenko
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Retrieves a list of books by the ID of the author.
     *
     * @param authorId The ID of the author for which to retrieve the books.
     * @return A list of books written by the specified author.
     */
    List<Book> findByAuthorId(Long authorId);
}


