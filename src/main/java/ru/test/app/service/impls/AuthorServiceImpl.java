package ru.test.app.service.impls;

import org.modelmapper.ModelMapper;
import ru.test.app.dto.AuthorDTO;
import ru.test.app.exception.BadRequestException;
import ru.test.app.exception.ResourceNotFoundException;
import ru.test.app.model.Author;
import ru.test.app.repo.AuthorRepository;
import ru.test.app.service.CommonService;
import ru.test.app.service.interfaces.AuthorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service implementation class for handling Author-related operations.
 * Author: Viacheslav Petrenko
 */
@Service
public class AuthorServiceImpl extends CommonService implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(final AuthorRepository authorRepository)
    {
        this.authorRepository = authorRepository;
    }

    /**
     * Saves an author. If an author with the same name already exists, throws an exception.
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
        return map(author, AuthorDTO.class);
    }
}

