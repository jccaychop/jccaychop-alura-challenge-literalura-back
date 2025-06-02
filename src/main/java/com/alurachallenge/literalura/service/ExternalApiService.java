package com.alurachallenge.literalura.service;

import com.alurachallenge.literalura.dto.AuthorDTO;
import com.alurachallenge.literalura.dto.BookDTO;
import com.alurachallenge.literalura.model.Author;
import com.alurachallenge.literalura.model.Book;
import com.alurachallenge.literalura.model.GutendexResponse;
import com.alurachallenge.literalura.repository.AuthorRepository;
import com.alurachallenge.literalura.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalApiService {

    @Value("${external.api.base-url}")
    private String apiUrl;

    @Autowired
    private ExternalApiCall externalApiCall;

    private final ConvertsData convertsData = new ConvertsData();

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private String encoderURL(String title) {
        return URLEncoder.encode(title, StandardCharsets.UTF_8);
    }

    private GutendexResponse getGutendexResponse(String text, String endpoint) {
        var json = externalApiCall.getData(apiUrl + endpoint + encoderURL(text));

        return convertsData.getData(json, GutendexResponse.class);
    }

    public List<BookDTO> searchBooks(String title, String endpoint) {
        List<Book> books = searchBook(title, endpoint);
        return books.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<Book> searchBook(String text, String endpoint) {
        GutendexResponse data = getGutendexResponse(text, endpoint);

        List<Book> books = data.books()
                .stream()
                .map(Book::new)
                .collect(Collectors.toList());

        for (Book book : books) {
            // 1) Resuelvo autores
            List<Author> resolvedAuthors = new ArrayList<>();
            for (Author author : book.getAuthors()) {
                Author savedAuthor = authorRepository.findByNameIgnoreCase(author.getName().trim())
                        .orElseGet(() -> authorRepository.save(author));
                resolvedAuthors.add(savedAuthor);
            }
            book.setAuthors(resolvedAuthors);

            // 2) Resuelvo traductores (¡esto faltaba!)
            List<Author> resolvedTranslators = new ArrayList<>();
            for (Author translator : book.getTranslators()) {
                Author savedTranslator = authorRepository.findByNameIgnoreCase(translator.getName().trim())
                        .orElseGet(() -> authorRepository.save(translator));
                resolvedTranslators.add(savedTranslator);
            }
            book.setTranslators(resolvedTranslators);

            // 3) Ahora sí guardo el Book, con autores y traductores ya persistidos
            Book saved = bookRepository.save(book);
            // System.out.println("Libro guardado: " + saved);
        }

        return books;
    }

    private BookDTO convertToDTO(Book book) {
        List<AuthorDTO> authorDTOs = book.getAuthors().stream()
                .map(author -> new AuthorDTO(
                        author.getId(),
                        author.getName(),
                        author.getBirthYear(),
                        author.getDeathYear()
                ))
                .collect(Collectors.toList());

        List<AuthorDTO> translatorDTOs = book.getTranslators().stream()
                .map(translator -> new AuthorDTO(
                        translator.getId(),
                        translator.getName(),
                        translator.getBirthYear(),
                        translator.getDeathYear()
                ))
                .collect(Collectors.toList());

        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getSummaries(),
                authorDTOs,
                translatorDTOs,
                book.getLanguages(),
                book.getFormats(),
                book.getDownloadCount()
        );
    }

}
