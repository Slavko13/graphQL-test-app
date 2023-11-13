package ru.test.app.service.impls;

import org.hibernate.Hibernate;
import ru.test.app.dto.AuthorDTO;
import ru.test.app.dto.BookDTO;
import ru.test.app.exception.BadRequestException;
import ru.test.app.exception.ResourceNotFoundException;
import ru.test.app.model.Author;
import ru.test.app.model.Book;
import ru.test.app.repo.AuthorRepository;
import ru.test.app.repo.BookRepository;
import ru.test.app.service.CommonService;
import ru.test.app.service.interfaces.AuthorService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service implementation class for handling Author-related operations.
 * Author: Viacheslav Petrenko
 */
@Service
public class AuthorServiceImpl extends CommonService implements AuthorService {

    private static final int MAX_ALLOWED_DISTANCE = 3;
    private final AuthorRepository authorRepository;

    private final BookRepository bookRepository;

    public AuthorServiceImpl(final AuthorRepository authorRepository, final BookRepository bookRepository)
    {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * Saves an author and his books if setted. If an author with the same name already exists, throws an exception.
     *
     * @param authorDTO The DTO representing the author.
     * @return The saved author.
     * @throws RuntimeException If an author with the same name already exists.
     */
    @Override
    @Transactional
    public AuthorDTO saveAuthor(final AuthorDTO.AuthorDTOInput authorDTO) {
        Optional<Author> existingAuthor = authorRepository.findByName(authorDTO.getName());

        if (existingAuthor.isPresent()) {
            throw new BadRequestException("Author with " + authorDTO.getName() + " already exists.");
        }

        Author author = map(authorDTO, Author.class);

        Author savedAuthor = authorRepository.save(author);
        List<Book> savedBooks = new ArrayList<>();

        if (authorDTO.getBooks() != null && !authorDTO.getBooks().isEmpty()) {
            for (BookDTO.BookDTOInput bookDTO : authorDTO.getBooks()) {
                Optional<Book> existingBook = bookRepository.findByTitleAndAuthorsIn(bookDTO.getTitle(), Collections.singletonList(savedAuthor));

                if (existingBook.isPresent()) {
                    savedAuthor.getBooks().add(existingBook.get());
                } else {
                    Book book = map(bookDTO, Book.class);
                    book.setAuthors(List.of(savedAuthor));
                    savedAuthor.getBooks().add(book);
                    savedBooks.add(bookRepository.save(book));
                }
            }
            savedAuthor.setBooks(savedBooks);
        } else {
            savedAuthor.setBooks(new ArrayList<>());
        }

        return map(savedAuthor, AuthorDTO.class);
    }


    /**
     * Retrieves a list of all authors.
     *
     * @return A list of all authors.
     */
    @Override
    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        return mapList(authors, AuthorDTO.class);
    }

    /**
     * Retrieves an author by name.
     *
     * @param name The name of the author.
     * @return The author with the specified name.
     * @throws ResourceNotFoundException If the author with the specified name is not found.
     */
    @Override
    public AuthorDTO getAuthorByName(final String name) {
        Optional<Author> authorOptional = authorRepository.findByName(name);

        if (authorOptional.isEmpty()) {
            throw new ResourceNotFoundException("Author with name " + name + " not found.");
        }

        Author author = authorOptional.get();
        Hibernate.initialize(author.getBooks());
        return map(author, AuthorDTO.class);
    }

    /**
     * Retrieves the author with the closest fuzzy name match to the given input name.
     *
     * @param inputName The input name to find a fuzzy match for.
     * @return The {@link AuthorDTO} with the closest fuzzy name match.
     * @throws ResourceNotFoundException If no author with a fuzzy name match is found within the maximum allowed distance.
     */
    @Override
    public AuthorDTO getAuthorByFuzzyName(String inputName) {
        int minDistance = Integer.MAX_VALUE;
        AuthorDTO closestAuthor = null;

        for (AuthorDTO author : getAllAuthors()) {
            int distance = calculateLevenshteinDistance(inputName, author.getName());
            if (distance < minDistance) {
                minDistance = distance;
                closestAuthor = author;
            }
        }

        if (minDistance <= MAX_ALLOWED_DISTANCE) {
            return closestAuthor;
        } else {
            throw new ResourceNotFoundException("Author with name " + inputName + " not found.");
        }
    }

    /**
     * Calculates the Levenshtein distance between two strings.
     *
     * @param str1 The first string.
     * @param str2 The second string.
     * @return The Levenshtein distance between the two strings.
     */
    private int calculateLevenshteinDistance(String str1, String str2) {
        int[][] dp = new int[str1.length() + 1][str2.length() + 1];

        for (int i = 0; i <= str1.length(); i++) {
            for (int j = 0; j <= str2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else {
                    dp[i][j] = min(
                            dp[i - 1][j - 1] + (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1
                    );
                }
            }
        }

        return dp[str1.length()][str2.length()];
    }

    /**
     * Finds the minimum value among three integers.
     *
     * @param a The first integer.
     * @param b The second integer.
     * @param c The third integer.
     * @return The minimum value among the three integers.
     */
    private int min(int a, int b, int c) {
        return Math.min(Math.min(a, b), c);
    }
}

