package com.alurachallenge.literalura.service;

import com.alurachallenge.literalura.api.response.ApiMessage;
import com.alurachallenge.literalura.api.response.ApiResponse;
import com.alurachallenge.literalura.api.response.SearchResultData;
import com.alurachallenge.literalura.dto.BookDTO;
import com.alurachallenge.literalura.dto.PersonDTO;
import com.alurachallenge.literalura.model.Book;
import com.alurachallenge.literalura.model.GutendexResponse;
import com.alurachallenge.literalura.model.Person;
import com.alurachallenge.literalura.repository.BookRepository;
import com.alurachallenge.literalura.repository.PersonRepository;
import com.alurachallenge.literalura.util.AppConstants;
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
    private PersonRepository personRepository;

    private String encoderURL(String text) {
        return URLEncoder.encode(text, StandardCharsets.UTF_8);
    }

    private GutendexResponse getGutendexResponse(String text, String endpoint) {
        var json = externalApiCall.getData(apiUrl + endpoint + encoderURL(text));
        return convertsData.getData(json, GutendexResponse.class);
    }

    /**
     * Convierte entidad Book → BookDTO, incluyendo authors y translators como PersonDTO.
     */
    private BookDTO convertToDTO(Book book) {
        List<PersonDTO> authorDTOs = book.getAuthors().stream()
                .map(person -> new PersonDTO(
                        person.getId(),
                        person.getName(),
                        person.getBirthYear(),
                        person.getDeathYear()
                ))
                .collect(Collectors.toList());

        List<PersonDTO> translatorDTOs = book.getTranslators().stream()
                .map(person -> new PersonDTO(
                        person.getId(),
                        person.getName(),
                        person.getBirthYear(),
                        person.getDeathYear()
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

    /**
     * ApiResponse<SearchResultData> para que el controlador pueda
     * informar cuántos libros se insertaron, cuántos fueron duplicados, etc.
     */
    public ApiResponse<SearchResultData> searchBooksWithResponse(String title, String endpoint) {
        GutendexResponse data = getGutendexResponse(title, endpoint);

        if (data.books() == null || data.books().isEmpty()) {
            return ApiResponse.failure(List.of(
                    new ApiMessage(AppConstants.CODE_NO_RESULTS, AppConstants.TEXT_NO_RESULTS)
            ));
        }

        int inserted = 0;
        int duplicates = 0;
        List<BookDTO> addedBooks = new ArrayList<>();

        for (var dataBook : data.books()) {
            String bookTitle = dataBook.title();

            if (bookRepository.existsByTitleIgnoreCase(bookTitle)) {
                duplicates++;
                continue;
            }

            // Crear entidad Book desde DataBook
            Book book = new Book(dataBook);

            // Resolver autores y traductores
            List<Person> resolvedAuthors = resolvePeople(book.getAuthors());
            List<Person> resolvedTranslators = resolvePeople(book.getTranslators());

            book.setAuthors(resolvedAuthors);
            book.setTranslators(resolvedTranslators);

            // Guardar libro
            Book saved = bookRepository.save(book);

            // Convertir a DTO y añadir a la lista de resultados
            addedBooks.add(convertToDTO(saved));
            inserted++;
        }

        // Preparar datos de resultado
        SearchResultData resultData = new SearchResultData(inserted, duplicates, addedBooks);

        // Preparar mensajes
        List<ApiMessage> messages = new ArrayList<>();
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, AppConstants.TEXT_SEARCH_COMPLETED));
        if (inserted > 0)
            messages.add(new ApiMessage(AppConstants.CODE_INSERTED, inserted + AppConstants.TEXT_CODE_INSERTED));
        if (duplicates > 0)
            messages.add(new ApiMessage(AppConstants.CODE_DUPLICATES, duplicates + AppConstants.TEXT_CODE_DUPLICATES));

        return ApiResponse.success(resultData, messages, addedBooks.size());
    }

    /**
     * Metodo auxiliar para resolver (persistir o recuperar) cada Person de una lista.
     */
    private List<Person> resolvePeople(List<Person> people) {
        List<Person> resolved = new ArrayList<>();
        for (Person person : people) {
            Person found = personRepository
                    .findByNameIgnoreCase(person.getName().trim())
                    .orElseGet(() -> personRepository.save(person));
            resolved.add(found);
        }
        return resolved;
    }
}
