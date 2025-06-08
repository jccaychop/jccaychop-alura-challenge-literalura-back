package com.alurachallenge.literalura.service;

import com.alurachallenge.literalura.api.response.ApiMessage;
import com.alurachallenge.literalura.api.response.ApiResponse;
import com.alurachallenge.literalura.api.response.BooksOnlyData;
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
     * Versión con ApiResponse<BooksOnlyData> para que el controlador pueda informar
     * cuantos la lista de libros almacenados en al BD y la cantidad.
     */
    public ApiResponse<BooksOnlyData> getAllBooks() {
        var data = convertDataBook(repository.findAll());

        if (data == null || data.isEmpty()) {
            return ApiResponse.failure(List.of(
                    new ApiMessage(AppConstants.CODE_NO_RESULTS, AppConstants.TEXT_NO_RESULTS_LIST)
            ));
        }

        BooksOnlyData resultData = new BooksOnlyData(data);

        List<ApiMessage> messages = new ArrayList<>();
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, AppConstants.TEXT_SEARCH_COMPLETED));

        return ApiResponse.success(resultData, messages);
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
