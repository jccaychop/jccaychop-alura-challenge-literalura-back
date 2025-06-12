package com.alurachallenge.literalura.dto;

import java.util.List;

public record AuthorWithBooksDTO(
        Long id,
        String name,
        Integer birthYear,
        Integer deathYear,
        List<BookSimpleDTO> books
) {
}
