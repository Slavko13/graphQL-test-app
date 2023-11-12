package ru.test.app.service;


import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Service;
import ru.test.app.dto.BookDTO;
import ru.test.app.service.interfaces.BookService;

import java.util.List;

/**
 * GraphQL resolver class for handling GraphQL queries and mutations related to book.
 * Author: Viacheslav Petrenko
 */
@Service
public class BookGraphQLResolver implements GraphQLQueryResolver, GraphQLMutationResolver
{

    private final BookService bookService;

    /**
     * Constructor for BookGraphQLResolver.
     *
     * @param bookService  The service for book-related operations.
     */
    public BookGraphQLResolver(BookService bookService) {
        this.bookService = bookService;
    }

    // Query Resolvers

    /**
     * Retrieves a list of all books.
     *
     * @return A list of all books.
     */
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
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
     * Saves a book.
     *
     * @param bookDTO The DTO representing the book to be saved.
     * @return The saved book.
     */
    public BookDTO saveBook(BookDTO.BookDTOInput bookDTO) {
        return bookService.saveBook(bookDTO);
    }

}
