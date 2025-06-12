package com.alurachallenge.literalura.controller;

import com.alurachallenge.literalura.api.response.ApiResponse;
import com.alurachallenge.literalura.api.response.AuthorListWithBooksResponse;
import com.alurachallenge.literalura.api.response.PersonListResponse;
import com.alurachallenge.literalura.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping("/authors")
    private ApiResponse<PersonListResponse> getAllAuthors() {
        return service.getAllAuthors();
    }

    @GetMapping("/authors/books")
    public ApiResponse<AuthorListWithBooksResponse> getAuthorsWithBooks(
            @RequestParam(value = "yearAlive", required = false) Integer yearAlive) {

        if (yearAlive != null) {
            return service.getAllAuthorsAliveInYearWithBooks(yearAlive);
        } else {
            return service.getAllAuthorsWithBooks();
        }
    }
}
