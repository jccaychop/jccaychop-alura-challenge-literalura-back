package com.alurachallenge.literalura.api.response;

import com.alurachallenge.literalura.dto.AuthorWithBooksDTO;

import java.util.List;

public class AuthorListWithBooksResponse {

    private List<AuthorWithBooksDTO> authors;

    public AuthorListWithBooksResponse(List<AuthorWithBooksDTO> authors) {
        this.authors = authors;
    }

    public List<AuthorWithBooksDTO> getAuthors() {
        return authors;
    }

    public void setAuthors(List<AuthorWithBooksDTO> authors) {
        this.authors = authors;
    }
}
