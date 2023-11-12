package ru.test.app.service;

import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.stereotype.Service;
import ru.test.app.dto.AuthorDTO;
import ru.test.app.service.interfaces.AuthorService;

import java.util.List;


/**
 * GraphQL resolver class for handling GraphQL queries and mutations related to authors.
 * Author: Viacheslav Petrenko
 */
@Service
public class AuthorGraphQLResolver implements GraphQLQueryResolver, GraphQLMutationResolver
{
    private final AuthorService authorService;


    /**
     * Constructor for GraphQLResolver.
     *
     * @param authorService The service for author-related operations.
     */
    public AuthorGraphQLResolver(final AuthorService authorService)
    {
        this.authorService = authorService;
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



}
