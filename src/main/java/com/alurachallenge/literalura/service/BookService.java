package com.alurachallenge.literalura.service;

import com.alurachallenge.literalura.api.response.ApiMessage;
import com.alurachallenge.literalura.api.response.ApiResponse;
import com.alurachallenge.literalura.api.response.BooksListResponse;
import com.alurachallenge.literalura.api.response.LanguageListResponse;
import com.alurachallenge.literalura.dto.BookDTO;
import com.alurachallenge.literalura.model.Book;
import com.alurachallenge.literalura.repository.BookRepository;
import com.alurachallenge.literalura.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    /**
     * Lista de todos los libros almacenados en la BD.
     */
    public ApiResponse<BooksListResponse> getAllBooks() {
        var data = convertDataBook(repository.findAll());

        if (data == null || data.isEmpty()) {
            return ApiResponse.failure(List.of(
                    new ApiMessage(AppConstants.CODE_NO_RESULTS, AppConstants.TEXT_NO_RESULTS_LIST)
            ));
        }

        BooksListResponse booksListResponse = new BooksListResponse(data);
        int count = booksListResponse.getBooks().size();

        List<ApiMessage> messages = new ArrayList<>();
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, AppConstants.TEXT_SEARCH_COMPLETED));
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, count + AppConstants.TEXT_RESULTS_LIST));

        return ApiResponse.success(booksListResponse, messages, count);
    }

    public ApiResponse<BooksListResponse> getAllBooksByLanguage(String language) {
        var data = convertDataBook(repository.findAllBooksByLanguages(language));

        if (data == null || data.isEmpty()) {
            return ApiResponse.failure(List.of(
                    new ApiMessage(AppConstants.CODE_NO_RESULTS, AppConstants.TEXT_NO_RESULTS_LIST)
            ));
        }

        BooksListResponse booksListResponse = new BooksListResponse(data);
        int count = booksListResponse.getBooks().size();

        List<ApiMessage> messages = new ArrayList<>();
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, AppConstants.TEXT_SEARCH_COMPLETED));
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, count + AppConstants.TEXT_RESULTS_LIST));

        return ApiResponse.success(booksListResponse, messages, count);
    }

    public ApiResponse<LanguageListResponse> getAllLanguages() {
        var data = repository.findAllDistinctLanguages();

        if (data == null || data.isEmpty()) {
            return ApiResponse.failure(List.of(
                    new ApiMessage(AppConstants.CODE_NO_RESULTS, AppConstants.TEXT_NO_RESULTS_LIST)
            ));
        }

        LanguageListResponse languageListResponse = new LanguageListResponse(data);
        int count = languageListResponse.getLanguages().size();

        List<ApiMessage> messages = new ArrayList<>();
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, AppConstants.TEXT_SEARCH_COMPLETED));
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, count + AppConstants.TEXT_RESULTS_LIST));

        return ApiResponse.success(languageListResponse, messages, count);
    }

    /**
     * Convierte una lista de Book -> lista de BookDTO
     */
    public List<BookDTO> convertDataBook(List<Book> books) {
        return books
                .stream()
                .map(book -> new BookDTO(
                        book.getId(),
                        book.getTitle(),
                        book.getSummaries(),
                        book.getAuthorsAsDTOs(),
                        book.getTranslatorsAsDTOs(),
                        book.getLanguages(),
                        book.getFormats(),
                        book.getDownloadCount()
                )).toList();
    }
}
