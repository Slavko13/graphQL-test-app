package ru.test.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) for Author entity.
 * Author: Viacheslav Petrenko
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    private Long id;
    private String name;
    private List<BookDTO> books;

    @Data
    public static class AuthorDTOInput {
        private Long id;
        private String name;
        private List<BookDTO.BookDTOInput> books;
    }
}

