package com.alurachallenge.literalura.dto;

import java.util.List;

public record BookDTO(
        Long id,
        String title,
        List<String> summaries,
        String languages,
        Integer downloadCount
) {
}
