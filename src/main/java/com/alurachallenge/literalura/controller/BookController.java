package com.alurachallenge.literalura.controller;

import com.alurachallenge.literalura.dto.BookDTO;
import com.alurachallenge.literalura.model.Book;
import com.alurachallenge.literalura.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private ExternalApiService externalApiService;

    @GetMapping("/books/search")
    public List<BookDTO> getBooksByTitle(@RequestParam String title) {
        return externalApiService.searchBooks(title, "/books/?search=");
    }
}
