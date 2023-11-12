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
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.test.app.dto.AuthorDTO;
import ru.test.app.dto.BookDTO;
import ru.test.app.exception.ResourceNotFoundException;
import ru.test.app.service.AuthorGraphQLResolver;
import ru.test.app.service.BookGraphQLResolver;

import javax.sql.DataSource;


/**
 * Integration tests for the GraphQL resolvers related to authors and books.
 *
 * These tests use Spring Boot's testing support along with Testcontainers for
 * setting up and managing a PostgreSQL container for the test database.
 *
 * Author: Viacheslav Petrenko
 */
@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = {AppApplication.class, GraphQLTest.TestConfig.class})
public class GraphQLTest
{

    @Autowired
    private AuthorGraphQLResolver authorGraphQLResolver;

    @Autowired
    private BookGraphQLResolver bookGraphQLResolver;

    private AuthorDTO savedAuthor;
    private BookDTO savedBook;

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");

    /**
     * Sets up the test environment by checking if an author with the given name
     * already exists. If not, creates a new author for testing purposes.
     */
    @BeforeEach
    public void setUp() {
        try {
            // Try to retrieve the author with the given name
            savedAuthor = authorGraphQLResolver.getAuthorByName("Test Author");
        } catch (ResourceNotFoundException e) {
            // If the author doesn't exist, create a new one
            AuthorDTO.AuthorDTOInput authorDTOInput = new AuthorDTO.AuthorDTOInput();
            authorDTOInput.setName("Test Author");
            savedAuthor = authorGraphQLResolver.saveAuthor(authorDTOInput);
        }

        // Check that the savedAuthor is not null and has a valid ID
        Assertions.assertNotNull(savedAuthor);
        Assertions.assertNotNull(savedAuthor.getId());
    }


    /**
     * Tests the creation of a book for an existing author.
     */
    @Test
    public void testCreateBook() {
        // Create a test book for the existing author
        BookDTO.BookDTOInput bookDTOInput = new BookDTO.BookDTOInput();
        bookDTOInput.setTitle("Test Book");
        bookDTOInput.setAuthorId(savedAuthor.getId());
        savedBook = bookGraphQLResolver.saveBook(bookDTOInput);

        Assertions.assertNotNull(savedBook);
        Assertions.assertNotNull(savedBook.getId());
    }

    /**
     * Tests the retrieval of an author by name from the repository.
     */
    @Test
    public void testGetAuthorByName() {
        // Retrieve author by name from the repository
        AuthorDTO retrievedAuthor = authorGraphQLResolver.getAuthorByName("Test Author");

        Assertions.assertNotNull(retrievedAuthor);
        Assertions.assertEquals(savedAuthor.getId(), retrievedAuthor.getId());
        Assertions.assertEquals(savedAuthor.getName(), retrievedAuthor.getName());
    }

    /**
     * Tests the retrieval of a book by author ID from the repository.
     */
    @Test
    public void testGetBookByAuthorId() {
        // Create a test book for the existing author
        BookDTO.BookDTOInput bookDTOInput = new BookDTO.BookDTOInput();
        bookDTOInput.setTitle("Test Book");
        bookDTOInput.setAuthorId(savedAuthor.getId());
        savedBook = bookGraphQLResolver.saveBook(bookDTOInput);

        Assertions.assertNotNull(savedBook);
        Assertions.assertNotNull(savedBook.getId());

        // Retrieve book by ID from the repository
        BookDTO retrievedBook = bookGraphQLResolver.getBooksByAuthorId(savedAuthor.getId()).get(0);

        Assertions.assertNotNull(retrievedBook);
        Assertions.assertEquals(savedBook.getId(), retrievedBook.getId());
        Assertions.assertEquals(savedBook.getTitle(), retrievedBook.getTitle());
    }

    /**
     * Configuration class for setting up the test DataSource and Flyway migration.
     */
    @TestConfiguration
    static class TestConfig {
        @Bean
        public DataSource dataSource() {
            return DataSourceBuilder
                    .create()
                    .url(postgresContainer.getJdbcUrl())
                    .username(postgresContainer.getUsername())
                    .password(postgresContainer.getPassword())
                    .driverClassName(postgresContainer.getDriverClassName())
                    .build();
        }

        /**
         * Configuration class for Flyway migration.
         */
        @Configuration
        public static class FlywayConfig {

            /**
             * Configures and executes Flyway migration.
             */
            @Bean
            public Flyway flyway(DataSource dataSource) {
                Flyway flyway = Flyway.configure()
                        .dataSource(dataSource)
                        .locations("classpath:db/migration")
                        .baselineOnMigrate(true)
                        .load();
                flyway.migrate();
                return flyway;
            }
        }
    }
}


