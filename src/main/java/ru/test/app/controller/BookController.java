package ru.test.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.test.app.dto.BookDTO;
import ru.test.app.service.interfaces.BookService;

import java.util.List;

/**
 * REST controller for handling operations related to books.
 * Author: Viacheslav Petrenko
 */
@RestController
@RequestMapping("/v1/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Get a list of all books.
     *
     * @return List of books.
     */
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    /**
     * Get a list of books by author.
     *
     * @param authorId Author's identifier.
     * @return List of books by the specified author.
     */
    @GetMapping("/by-author/{authorId}")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable Long authorId) {
        List<BookDTO> books = bookService.getBooksByAuthorId(authorId);
        return ResponseEntity.ok(books);
    }

    /**
     * Save a new book.
     *
     * @param bookDTO Information about the new book.
     * @return Information about the saved book.
     */
    @PostMapping
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO.BookDTOInput bookDTO) {
        BookDTO savedBook = bookService.saveBook(bookDTO);
        return ResponseEntity.ok(savedBook);
    }
}

