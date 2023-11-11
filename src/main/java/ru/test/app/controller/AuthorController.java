package ru.test.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.test.app.dto.AuthorDTO;
import ru.test.app.service.interfaces.AuthorService;

import java.util.List;

/**
 * REST controller for handling operations related to authors.
 * Author: Viacheslav Petrenko
 */
@RestController
@RequestMapping("v1/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Get a list of all authors.
     *
     * @return List of authors.
     */
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        List<AuthorDTO> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    /**
     * Get an author by name.
     *
     * @param name Author's name.
     * @return Information about the found author.
     */
    @GetMapping("/{name}")
    public ResponseEntity<AuthorDTO> getAuthorByName(@PathVariable String name) {
        AuthorDTO author = authorService.getAuthorByName(name);
        return ResponseEntity.ok(author);
    }

    /**
     * Save a new author.
     *
     * @param authorDTO Information about the new author.
     * @return Information about the saved author.
     */
    @PostMapping
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody AuthorDTO.AuthorDTOInput authorDTO) {
        AuthorDTO savedAuthor = authorService.saveAuthor(authorDTO);
        return ResponseEntity.ok(savedAuthor);
    }
}

