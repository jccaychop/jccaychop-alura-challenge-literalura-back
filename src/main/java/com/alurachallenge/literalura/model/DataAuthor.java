package com.alurachallenge.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DataAuthor(
        String name,

        @JsonAlias("birth_year")
        Integer birthYear,

        @JsonAlias("death_year")
        Integer deathYear
) {
}
