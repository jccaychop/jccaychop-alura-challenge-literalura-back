package com.alurachallenge.literalura.controller;

import com.alurachallenge.literalura.api.response.ApiResponse;
import com.alurachallenge.literalura.api.response.SearchResultData;
import com.alurachallenge.literalura.dto.BookDTO;
import com.alurachallenge.literalura.repository.BookRepository;
import com.alurachallenge.literalura.service.ExternalApiService;
import com.alurachallenge.literalura.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ExternalApiService externalApiService;

    @GetMapping
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new BookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getSummaries(),
                        book.getAuthorsAsDTOs(),
                        book.getTranslatorsAsDTOs(),
                        book.getLanguages(),
                        book.getFormats(),
                        book.getDownloadCount()
                ))
                .toList();
    }

    @GetMapping("/books/search")
    public ApiResponse<SearchResultData> searchBooks(@RequestParam String title) {
        return externalApiService.searchBooksWithResponse(title, AppConstants.ENDPOINT_SEARCH_BY_TITLE);
    }
}
