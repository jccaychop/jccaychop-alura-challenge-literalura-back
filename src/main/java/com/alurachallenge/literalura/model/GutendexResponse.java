package com.alurachallenge.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexResponse(
        @JsonAlias("results")
        List<DataBook> books
) {
}
