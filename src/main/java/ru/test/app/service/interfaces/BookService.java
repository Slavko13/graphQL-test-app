package ru.test.app.service.interfaces;

import org.springframework.stereotype.Service;
import ru.test.app.dto.AuthorDTO;
import ru.test.app.dto.BookDTO;

import java.util.List;

/**
 * Service interface for handling Book-related operations.
 * Author: Viacheslav Petrenko
 */
@Service
public interface BookService {

    /**
     * Retrieves a list of books by a given author.
     *
     * @param authorId representing the author id for search.
     * @return A list of books written by the specified author.
     */
    List<BookDTO> getBooksByAuthorId(Long authorId);

    /**
     * Retrieves a list of all books.
     *
     * @return A list of all books.
     */
    List<BookDTO> getAllBooks();

    /**
     * Saves a list of books.
     *
     * @param bookDTOList The list of DTOs representing the books.
     * @return A list of saved books.
     */
    List<BookDTO> saveAllBooks(List<BookDTO> bookDTOList);


    /**
     * Saves a single book.
     *
     * @param bookDTO The DTO representing the book.
     * @return The saved book.
     */
    BookDTO saveBook(BookDTO.BookDTOInput bookDTO);
}

