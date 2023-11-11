package ru.test.app.service;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.test.app.dto.AuthorDTO;
import ru.test.app.dto.BookDTO;
import ru.test.app.service.interfaces.AuthorService;
import ru.test.app.service.interfaces.BookService;

import java.util.List;

/**
 * GraphQL resolver class for handling GraphQL queries and mutations related to authors and books.
 * Author: Viacheslav Petrenko
 */
@Service
public class GraphQLResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    private final AuthorService authorService;
    private final BookService bookService;

    /**
     * Constructor for GraphQLResolver.
     *
     * @param authorService The service for author-related operations.
     * @param bookService   The service for book-related operations.
     */
    @Autowired
    public GraphQLResolver(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    // Query Resolvers

    /**
     * Retrieves a list of all authors.
     *
     * @return A list of all authors.
     */
    public List<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    /**
     * Retrieves an author by name.
     *
     * @param name The name of the author.
     * @return The author with the specified name.
     */
    public AuthorDTO getAuthorByName(String name) {
        return authorService.getAuthorByName(name);
    }

    /**
     * Retrieves a list of all books.
     *
     * @return A list of all books.
     */
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    public String hello() {
        return "Hello, GraphQL!";
    }

    /**
     * Retrieves a list of books by a given author.
     *
     * @param authorId Representing the author id for search.
     * @return A list of books written by the specified author.
     */
    public List<BookDTO> getBooksByAuthorId(Long authorId) {
        return bookService.getBooksByAuthorId(authorId);
    }

    // Mutation Resolvers

    /**
     * Saves an author.
     *
     * @param authorDTO The DTO representing the author to be saved.
     * @return The saved author.
     */
    public AuthorDTO saveAuthor(AuthorDTO.AuthorDTOInput authorDTO) {
        return authorService.saveAuthor(authorDTO);
    }

    /**
     * Saves a book.
     *
     * @param bookDTO The DTO representing the book to be saved.
     * @return The saved book.
     */
    public BookDTO saveBook(BookDTO.BookDTOInput bookDTO) {
        return bookService.saveBook(bookDTO);
    }
}


