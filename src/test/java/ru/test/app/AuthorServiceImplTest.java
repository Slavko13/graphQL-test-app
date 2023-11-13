package ru.test.app;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.test.app.config.TestConfig;
import ru.test.app.dto.AuthorDTO;
import ru.test.app.dto.BookDTO;
import ru.test.app.exception.BadRequestException;
import ru.test.app.exception.ResourceNotFoundException;
import ru.test.app.model.Author;
import ru.test.app.model.Book;
import ru.test.app.repo.AuthorRepository;
import ru.test.app.repo.BookRepository;
import ru.test.app.service.impls.AuthorServiceImpl;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the AuthorServiceImpl.
 *
 * These tests use Spring Boot's testing support along with Testcontainers for
 * setting up and managing a PostgreSQL container for the test database.
 *
 * Author: Viacheslav Petrenko
 */
@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = {AppApplication.class, TestConfig.class})
@ActiveProfiles("test")
public class AuthorServiceImplTest {

    @Autowired
    private AuthorServiceImpl authorService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");


    /**
     * Test saving a new author.
     */
    @Test
    public void testSaveAuthor() {
        // Arrange
        AuthorDTO.AuthorDTOInput authorDTOInput = new AuthorDTO.AuthorDTOInput();
        authorDTOInput.setName("Anna Smith");

        AuthorDTO savedAuthor = authorService.saveAuthor(authorDTOInput);

        assertNotNull(savedAuthor.getId());
        assertEquals("Anna Smith", savedAuthor.getName());

        Optional<Author> retrievedAuthor = authorRepository.findById(savedAuthor.getId());
        assertTrue(retrievedAuthor.isPresent());
    }

    /**
     * Test saving a new author with associated books.
     */
    @Test
    public void testSaveAuthorWithBooks() {
        AuthorDTO.AuthorDTOInput authorDTOInput = new AuthorDTO.AuthorDTOInput();
        authorDTOInput.setName("Ivan Chernov");

        List<BookDTO.BookDTOInput> bookDTOInputs = new ArrayList<>();
        bookDTOInputs.add(new BookDTO.BookDTOInput("Book 1"));
        bookDTOInputs.add(new BookDTO.BookDTOInput("Book 2"));
        authorDTOInput.setBooks(bookDTOInputs);

        AuthorDTO savedAuthor = authorService.saveAuthor(authorDTOInput);

        assertNotNull(savedAuthor.getId());
        assertEquals("Ivan Chernov", savedAuthor.getName());

        Optional<Author> retrievedAuthor = authorRepository.findById(savedAuthor.getId());
        assertTrue(retrievedAuthor.isPresent());

        List<Book> savedBooks = bookRepository.findBooksByAuthorId(savedAuthor.getId());
        assertEquals(2, savedBooks.size());

    }

    /**
     * Test attempting to save an author with an existing name.
     */
    @Test
    public void testSaveAuthorWithExistingAuthor() {
        AuthorDTO.AuthorDTOInput authorDTOInput = new AuthorDTO.AuthorDTOInput();
        authorDTOInput.setName("Maria Garcia");

        AuthorDTO existingAuthor = authorService.saveAuthor(authorDTOInput);

        assertThrows(BadRequestException.class, () -> authorService.saveAuthor(authorDTOInput));
    }

    /**
     * Test retrieving an author by name.
     */
    @Test
    public void testGetAuthorByName() {
        String authorName = "Alexander Kim";
        AuthorDTO.AuthorDTOInput authorDTOInput = new AuthorDTO.AuthorDTOInput();
        authorDTOInput.setName(authorName);
        AuthorDTO savedAuthor = authorService.saveAuthor(authorDTOInput);

        AuthorDTO retrievedAuthor = authorService.getAuthorByName(authorName);

        assertNotNull(retrievedAuthor);
        assertEquals(savedAuthor.getId(), retrievedAuthor.getId());
        assertEquals(savedAuthor.getName(), retrievedAuthor.getName());
    }

    /**
     * Test attempting to retrieve an author by a nonexistent name.
     */
    @Test
    public void testGetAuthorByNameNotFound() {
        String authorName = "Nonexistent Author";
        assertThrows(ResourceNotFoundException.class, () -> authorService.getAuthorByName(authorName));
    }

    // /**
    //  * Test retrieving an author by fuzzy name.
    //  */
    // @Test
    // public void testGetAuthorByFuzzyName() {
    //     String inputName = "Slava Petrenko";
    //     AuthorDTO.AuthorDTOInput authorDTOInput = new AuthorDTO.AuthorDTOInput();
    //     authorDTOInput.setName(inputName);
    //     AuthorDTO savedAuthor = authorService.saveAuthor(authorDTOInput);
    //     String inputNameWithErrors = "Slva Ptrenko";
    //     AuthorDTO retrievedAuthor = authorService.getAuthorByFuzzyName(inputNameWithErrors);
    //     assertNotNull(retrievedAuthor);
    //     assertEquals(savedAuthor.getId(), retrievedAuthor.getId());
    //     assertEquals(savedAuthor.getName(), retrievedAuthor.getName());
    // }
}


