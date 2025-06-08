package com.alurachallenge.literalura.api.response;

import com.alurachallenge.literalura.dto.BookDTO;

import java.util.List;

public class BooksListResponse {

    private List<BookDTO> books;

    public BooksListResponse(List<BookDTO> books) {
        this.books = books;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
