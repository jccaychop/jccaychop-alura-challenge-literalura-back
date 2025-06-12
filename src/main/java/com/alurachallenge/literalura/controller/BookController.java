package com.alurachallenge.literalura.controller;

import com.alurachallenge.literalura.api.response.ApiResponse;
import com.alurachallenge.literalura.api.response.BooksListResponse;
import com.alurachallenge.literalura.api.response.LanguageListResponse;
import com.alurachallenge.literalura.api.response.SearchResultData;
import com.alurachallenge.literalura.service.BookService;
import com.alurachallenge.literalura.service.ExternalApiService;
import com.alurachallenge.literalura.util.AppConstants;
import io.swagger.v3.oas.annotations.Parameter;
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
    public ApiResponse<BooksListResponse> getAllBooks() {
        return service.getAllBooks();
    }

    @GetMapping("/books/languages")
    private ApiResponse<LanguageListResponse> getAllLanguagesFromBooks() {
        return service.getAllLanguages();
    }



    @GetMapping("/books/languages/{language}")
    public ApiResponse<BooksListResponse> getAllBooksByLanguage(
            @Parameter(
                    description = "Código de idioma en formato ISO 639-1 (por ejemplo, 'es' para español, 'en' para inglés)",
                    example = "es"
            )
            @PathVariable String language
    ) {
        return service.getAllBooksByLanguage(language);
    }


    // petición a la API externa
    // /books/search?title=
    @GetMapping("/books/search")
    public ApiResponse<SearchResultData> searchBooks(@RequestParam String title) {
        return externalApiService.searchBooksWithResponse(title, AppConstants.ENDPOINT_SEARCH_BY_TITLE);
    }
}
