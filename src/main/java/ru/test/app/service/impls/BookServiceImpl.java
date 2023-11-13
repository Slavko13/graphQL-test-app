package ru.test.app.service.impls;

import org.springframework.transaction.annotation.Transactional;
import ru.test.app.dto.AuthorDTO;
import ru.test.app.dto.BookDTO;
import ru.test.app.model.Author;
import ru.test.app.model.Book;
import ru.test.app.repo.AuthorRepository;
import ru.test.app.repo.BookRepository;
import ru.test.app.service.CommonService;
import ru.test.app.service.interfaces.BookService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Service implementation class for handling Book-related operations.
 * Author: Viacheslav Petrenko
 */
@Service
public class BookServiceImpl extends CommonService implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(final BookRepository bookRepository, final AuthorRepository authorRepository)
    {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    /**
     * Retrieves a list of books by a given author.
     *
     * @param authorId  representing the author id for search.
     * @return A list of books written by the specified author.
     */
    @Override
    public List<BookDTO> getBooksByAuthorId(final Long authorId) {
        List<Book> books = bookRepository.findBooksByAuthorId(authorId);
        return mapList(books, BookDTO.class);
    }

    /**
     * Retrieves a list of all books.
     *
     * @return A list of all books.
     */
    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return mapList(books, BookDTO.class);
    }

    /**
     * Saves a list of books.
     *
     * @param bookDTOList The list of DTOs representing the books.
     * @return A list of saved books.
     */
    @Override
    public List<BookDTO> saveAllBooks(final List<BookDTO> bookDTOList) {
        List<Book> booksToSave = bookDTOList.stream()
                .map(bookDTO -> map(bookDTO, Book.class))
                .collect(Collectors.toList());

        List<Book> savedBooks = bookRepository.saveAll(booksToSave);
        return mapList(savedBooks, BookDTO.class);
    }

    /**
     * Saves a single book.
     *
     * @param bookDTO The DTO representing the book.
     * @return The saved book.
     */
    @Override
    @Transactional
    public BookDTO saveBook(BookDTO.BookDTOInput bookDTO) {
        List<AuthorDTO.AuthorDTOInput> authorDTOInputList = bookDTO.getAuthors();
        List<AuthorDTO> authorDTOList = new ArrayList<>();
        List<Author> authorList = new ArrayList<>(); // Добавлен список для хранения управляемых авторов

        // Логика сохранения авторов
        for (AuthorDTO.AuthorDTOInput authorDTOInput : authorDTOInputList) {
            Author existingAuthor = null;
            if (authorDTOInput.getId() != null) {
                existingAuthor = authorRepository.findById(authorDTOInput.getId()).orElse(null);
            }
            if (existingAuthor == null) {
                // Автора не существует, создаем нового
                Author newAuthor = new Author();
                newAuthor.setId(authorDTOInput.getId());
                newAuthor.setName(authorDTOInput.getName());
                existingAuthor = authorRepository.save(newAuthor);
            }
            authorDTOList.add(map(existingAuthor, AuthorDTO.class));
            authorList.add(existingAuthor); // Добавляем управляемого автора в список
        }

        // Логика сохранения книги
        Book bookToSave = new Book();
        bookToSave.setTitle(bookDTO.getTitle());
        bookToSave.setAuthors(authorList); // Используем управляемых авторов
        Book savedBook = bookRepository.save(bookToSave);

        return map(savedBook, BookDTO.class);
    }

}

