package com.alurachallenge.literalura.api.response;

import com.alurachallenge.literalura.dto.BookDTO;

import java.util.List;

public class BooksOnlyData {
    private List<BookDTO> books;
    private int count;

    public BooksOnlyData() {
    }

    public BooksOnlyData(List<BookDTO> books) {
        this.books = books;
        this.count = books.size();
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
