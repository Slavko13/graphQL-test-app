package ru.test.app.service.interfaces;

import org.springframework.stereotype.Service;
import ru.test.app.dto.AuthorDTO;

import java.util.List;

/**
 * Service interface for handling Author-related operations.
 * Author: Viacheslav Petrenko
 */
@Service
public interface AuthorService {

    /**
     * Saves an author. If an author with the same name already exists, throws an exception.
     *
     * @param authorDTO The DTO representing the author.
     * @return The saved author.
     * @throws RuntimeException If an author with the same name already exists.
     */
    AuthorDTO saveAuthor(AuthorDTO.AuthorDTOInput authorDTO);

    /**
     * Retrieves a list of all authors.
     *
     * @return A list of all authors.
     */
    List<AuthorDTO> getAllAuthors();

    /**
     * Retrieves an author by name.
     *
     * @param name The name of the author.
     * @return The author with the specified name.
     * @throws RuntimeException If the author with the specified name is not found.
     */
    AuthorDTO getAuthorByName(String name);
}

