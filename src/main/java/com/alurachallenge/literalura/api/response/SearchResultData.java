package com.alurachallenge.literalura.api.response;

import com.alurachallenge.literalura.dto.BookDTO;

import java.util.List;

/**
 * DTO que agrupa el resultado de la búsqueda:
 * - lista de BookDTO encontrados
 * - cuántos se insertaron en la BD
 * - cuántos se ignoraron por duplicados
 */
public class SearchResultData {
    private int inserted;
    private int duplicates;
    private List<BookDTO> books;

    public SearchResultData(int inserted, int duplicates, List<BookDTO> books) {
        this.inserted = inserted;
        this.duplicates = duplicates;
        this.books = books;
    }

    // Getters y setters necesarios para Jackson

    public int getInserted() {
        return inserted;
    }

    public void setInserted(int inserted) {
        this.inserted = inserted;
    }

    public int getDuplicates() {
        return duplicates;
    }

    public void setDuplicates(int duplicates) {
        this.duplicates = duplicates;
    }

    public List<BookDTO> getBooks() {
        return books;
    }

    public void setBooks(List<BookDTO> books) {
        this.books = books;
    }
}
