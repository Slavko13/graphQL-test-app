package ru.test.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.test.app.config.TestConfig;
import ru.test.app.dto.AuthorDTO;
import ru.test.app.dto.BookDTO;
import ru.test.app.model.Author;
import ru.test.app.model.Book;
import ru.test.app.repo.AuthorRepository;
import ru.test.app.repo.BookRepository;
import ru.test.app.service.impls.AuthorServiceImpl;
import ru.test.app.service.impls.BookServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link BookServiceImpl} class.
 */
@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = {AppApplication.class, TestConfig.class})
@ActiveProfiles("test")
class BookServiceImplTest
{

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;



    /**
     * Tests the {@link BookServiceImpl#getBooksByAuthorId(Long)} method.
     */
    @Test
    @Transactional
    void getBooksByAuthorId()
    {
        // Arrange
        Author author = new Author();
        author.setName("Leo Tolstoy");
        Author savedAuthor = authorRepository.save(author);

        Book book = new Book();
        book.setTitle("War and Peace");
        book.setAuthors(Collections.singletonList(savedAuthor));
        bookRepository.save(book);
        List<BookDTO> result = bookService.getBooksByAuthorId(savedAuthor.getId());

        assertEquals(1, result.size());
        assertEquals("War and Peace", result.get(0).getTitle());
    }


    /**
     * Tests the {@link BookServiceImpl#saveBook(BookDTO.BookDTOInput)} method.
     */
    @Test
    @BeforeEach
    void saveBook()
    {
        // Arrange
        AuthorDTO.AuthorDTOInput authorDTOInput = new AuthorDTO.AuthorDTOInput();
        authorDTOInput.setName("Harper Lee");

        BookDTO.BookDTOInput bookDTOInput = new BookDTO.BookDTOInput("To Kill a Mockingbird");
        bookDTOInput.setAuthors(Collections.singletonList(authorDTOInput));

        // Act
        BookDTO result = bookService.saveBook(bookDTOInput);

        // Assert
        assertEquals("To Kill a Mockingbird", result.getTitle());
        assertEquals(1, result.getAuthors().size());
        assertEquals("Harper Lee", result.getAuthors().get(0).getName());
    }
}

