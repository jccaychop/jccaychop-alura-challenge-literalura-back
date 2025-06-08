package com.alurachallenge.literalura.controller;

import com.alurachallenge.literalura.api.response.ApiResponse;
import com.alurachallenge.literalura.api.response.BooksListResponse;
import com.alurachallenge.literalura.api.response.SearchResultData;
import com.alurachallenge.literalura.service.BookService;
import com.alurachallenge.literalura.service.ExternalApiService;
import com.alurachallenge.literalura.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookService service;

    @Autowired
    private ExternalApiService externalApiService;

    @GetMapping("/books")
    public ApiResponse<BooksListResponse> getBooks() {
        return service.getAllBooks();
    }

    // petición a la API externa
    // /books/search?title=
    @GetMapping("/books/search")
    public ApiResponse<SearchResultData> searchBooks(@RequestParam String title) {
        return externalApiService.searchBooksWithResponse(title, AppConstants.ENDPOINT_SEARCH_BY_TITLE);
    }
}
