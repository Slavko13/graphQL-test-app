package ru.test.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<AuthorDTO> authors;

    @Data
    public static class BookDTOInput {
        private Long id;
        private String title;
        private List<AuthorDTO.AuthorDTOInput> authors;


        public BookDTOInput(final String title)
        {
            this.title = title;
        }
    }
}

