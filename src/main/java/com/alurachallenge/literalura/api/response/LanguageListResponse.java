package com.alurachallenge.literalura.api.response;

import java.util.List;

public class LanguageListResponse {

    private List<String> languages;

    public LanguageListResponse(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }
}
