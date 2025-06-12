package com.alurachallenge.literalura.service;

import com.alurachallenge.literalura.api.response.ApiMessage;
import com.alurachallenge.literalura.api.response.ApiResponse;
import com.alurachallenge.literalura.api.response.AuthorListWithBooksResponse;
import com.alurachallenge.literalura.api.response.PersonListResponse;
import com.alurachallenge.literalura.dto.AuthorWithBooksDTO;
import com.alurachallenge.literalura.dto.BookSimpleDTO;
import com.alurachallenge.literalura.dto.PersonDTO;
import com.alurachallenge.literalura.model.Person;
import com.alurachallenge.literalura.repository.PersonRepository;
import com.alurachallenge.literalura.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository repository;

    public ApiResponse<PersonListResponse> getAllAuthors() {
        var data = convertDataPerson(repository.findByAuthoredBooksIsNotEmpty());

        if (data == null || data.isEmpty()) {
            return ApiResponse.failure(List.of(
                    new ApiMessage(AppConstants.CODE_NO_RESULTS, AppConstants.TEXT_NO_RESULTS_LIST)
            ));
        }

        PersonListResponse personListResponse = new PersonListResponse(data);
        int count = personListResponse.getPersons().size();

        List<ApiMessage> messages = new ArrayList<>();
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, AppConstants.TEXT_SEARCH_COMPLETED));
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, count + AppConstants.TEXT_RESULTS_LIST));

        return ApiResponse.success(personListResponse, messages, count);
    }

    public ApiResponse<AuthorListWithBooksResponse> getAllAuthorsWithBooks() {

        var data = convertDataPersonToAuthorWithBooks(repository.findByAuthoredBooksIsNotEmpty());

        if (data.isEmpty()) {
            return ApiResponse.failure(List.of(
                    new ApiMessage(AppConstants.CODE_NO_RESULTS, AppConstants.TEXT_NO_RESULTS_LIST)
            ));
        }

        AuthorListWithBooksResponse authorListWithBooksResponse = new AuthorListWithBooksResponse(data);

        int count = authorListWithBooksResponse.getAuthors().size();

        List<ApiMessage> messages = new ArrayList<>();
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, AppConstants.TEXT_SEARCH_COMPLETED));
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, count + AppConstants.TEXT_RESULTS_LIST));

        return ApiResponse.success(authorListWithBooksResponse, messages, count);

    }

    public ApiResponse<AuthorListWithBooksResponse> getAllAuthorsAliveInYearWithBooks(int year) {

        var data = convertDataPersonToAuthorWithBooks(repository.findAuthorsAliveInYearWithBooks(year));

        if (data.isEmpty()) {
            return ApiResponse.failure(List.of(
                    new ApiMessage(AppConstants.CODE_NO_RESULTS, AppConstants.TEXT_NO_RESULTS_LIST)
            ));
        }

        AuthorListWithBooksResponse authorListWithBooksResponse = new AuthorListWithBooksResponse(data);

        int count = authorListWithBooksResponse.getAuthors().size();

        List<ApiMessage> messages = new ArrayList<>();
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, AppConstants.TEXT_SEARCH_COMPLETED));
        messages.add(new ApiMessage(AppConstants.CODE_SUCCESS, count + AppConstants.TEXT_RESULTS_LIST));

        return ApiResponse.success(authorListWithBooksResponse, messages, count);
    }

    /* CONVERT DATA */

    public List<PersonDTO> convertDataPerson(List<Person> persons) {
        return persons
                .stream()
                .map(person -> new PersonDTO(
                        person.getId(),
                        person.getName(),
                        person.getBirthYear(),
                        person.getDeathYear()
                ))
                .collect(Collectors.toList());
    }

    public List<AuthorWithBooksDTO> convertDataPersonToAuthorWithBooks(List<Person> persons) {
        return persons
                .stream()
                .map(person -> new AuthorWithBooksDTO(
                        person.getId(),
                        person.getName(),
                        person.getBirthYear(),
                        person.getDeathYear(),
                        person.getAuthoredBooks().stream()
                                .map(book -> new BookSimpleDTO(
                                        book.getId(),
                                        book.getTitle()
                                )).toList()
                )).toList();
    }
}
