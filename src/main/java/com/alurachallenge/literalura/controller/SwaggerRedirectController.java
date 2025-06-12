package com.alurachallenge.literalura.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

    @GetMapping("/")
    public String redirectToSwaggerUI() {
        return "redirect:/swagger-ui/index.html";
    }
}
