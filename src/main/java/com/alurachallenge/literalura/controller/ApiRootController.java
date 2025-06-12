package com.alurachallenge.literalura.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ApiRootController {

    @GetMapping("/api")
    public Map<String, String> index() {
        return Map.of(
                "message", "Bienvenido a la API. Visita /swagger-ui/index.html para la documentación.",
                "docs", "/swagger-ui/index.html"
        );
    }
}
