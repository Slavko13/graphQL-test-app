package ru.test.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.test.app.model.Author;
import ru.test.app.model.Book;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


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
    @Query(value = "SELECT b.* FROM book b JOIN author_book ab ON b.id = ab.book_id WHERE ab.author_id = :authorId", nativeQuery = true)
    List<Book> findBooksByAuthorId(@Param("authorId") Long authorId);

    Optional<Book> findByTitleAndAuthorsIn(String title, List<Author> ids);
}


