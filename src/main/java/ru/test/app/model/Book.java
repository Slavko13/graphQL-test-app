package ru.test.app.model;

import jakarta.persistence.*;
import lombok.*;


/**
 * Entity representing a book.
 * Author: Viacheslav Petrenko
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}

