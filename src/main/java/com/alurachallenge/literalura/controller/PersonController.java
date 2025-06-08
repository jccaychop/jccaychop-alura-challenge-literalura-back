package com.alurachallenge.literalura.controller;

import com.alurachallenge.literalura.api.response.ApiResponse;
import com.alurachallenge.literalura.api.response.PersonListResponse;
import com.alurachallenge.literalura.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PersonController {

    @Autowired
    private PersonService service;

    @GetMapping("/authors")
    private ApiResponse<PersonListResponse> getAuthors() {
        return service.getAllAuthors();
    }
}
