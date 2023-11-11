package ru.test.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for Book entity.
 * Author: Viacheslav Petrenko
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;
    private String title;
    private AuthorDTO author;




    @Data
    public static class BookDTOInput {
        private String title;
        private Long authorId;

    }

}

