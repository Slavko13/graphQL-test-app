package ru.test.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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


    @Data
    public static class AuthorDTOInput {
        private String name;

    }
}

