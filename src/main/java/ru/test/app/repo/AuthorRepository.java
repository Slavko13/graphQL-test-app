package ru.test.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.test.app.model.Author;

import java.util.Optional;


/**
 * Repository interface for the Author entity.
 * Author: Viacheslav Petrenko
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Retrieves an author by first name and last name.
     *
     * @param name of the author.
     * @return An Optional containing the author with the specified first name and last name, or empty if not found.
     */
    Optional<Author> findByName(String name);
}

