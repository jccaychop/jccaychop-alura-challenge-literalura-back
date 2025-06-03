package com.alurachallenge.literalura.dto;

import java.util.List;
import java.util.Map;

public record BookDTO(
        Long id,
        String title,
        List<String> summaries,
        List<PersonDTO> authors,
        List<PersonDTO> translators,
        List<String> languages,
        Map<String, String> formats,
        Integer downloadCount
) {
}
