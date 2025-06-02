package com.alurachallenge.literalura.dto;

public record AuthorDTO(
        Long id,
        String name,
        Integer birthYear,
        Integer deathYear
) {
}
