package ru.test.app;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.test.app.dto.AuthorDTO;
import ru.test.app.dto.BookDTO;
import ru.test.app.repo.AuthorRepository;
import ru.test.app.repo.BookRepository;
import ru.test.app.service.GraphQLResolver;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@SpringBootTest
@Testcontainers
@ContextConfiguration(classes = {AppApplication.class, TestTest.TestConfig.class})
public class TestTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private GraphQLResolver graphQLResolver;

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");

    @Test
    public void TestTestHi() {
        AuthorDTO.AuthorDTOInput authorDTOInput = new AuthorDTO.AuthorDTOInput();
        authorDTOInput.setName("Test Author");
        AuthorDTO savedAuthor = graphQLResolver.saveAuthor(authorDTOInput);

        Assertions.assertNotNull(savedAuthor);
        Assertions.assertNotNull(savedAuthor.getId());

        // Создаем тестовую книгу
        BookDTO.BookDTOInput bookDTOInput = new BookDTO.BookDTOInput();
        bookDTOInput.setTitle("Test Book");
        bookDTOInput.setAuthorId(savedAuthor.getId());
        BookDTO savedBook = graphQLResolver.saveBook(bookDTOInput);

        Assertions.assertNotNull(savedBook);
        Assertions.assertNotNull(savedBook.getId());

        // Извлекаем автора по имени через репозиторий
        AuthorDTO retrievedAuthor = graphQLResolver.getAuthorByName("Test Author");

        Assertions.assertNotNull(retrievedAuthor);
        Assertions.assertEquals(savedAuthor.getId(), retrievedAuthor.getId());
        Assertions.assertEquals(savedAuthor.getName(), retrievedAuthor.getName());

        // Извлекаем книгу по ID через репозиторий
        BookDTO retrievedBook = graphQLResolver.getBooksByAuthorId(savedAuthor.getId()).get(0);

        Assertions.assertNotNull(retrievedBook);
        Assertions.assertEquals(savedBook.getId(), retrievedBook.getId());
        Assertions.assertEquals(savedBook.getTitle(), retrievedBook.getTitle());
    }

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

        @Configuration
        public static class FlywayConfig {

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

