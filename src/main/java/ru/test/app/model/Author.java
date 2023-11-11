package ru.test.app.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * Entity representing an author.
 * Author: Viacheslav Petrenko
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Book> books;
}

