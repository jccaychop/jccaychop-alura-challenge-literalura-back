package com.alurachallenge.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook(
        String title,

        List<DataAuthor> authors,

        List<String> summaries,

        List<DataAuthor> translators,

        List<String> languages,

        Map<String, String> formats,

        @JsonAlias("download_count")
        Integer downloadCount
) {
}
