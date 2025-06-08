package com.alurachallenge.literalura.api.response;

import com.alurachallenge.literalura.dto.PersonDTO;

import java.util.List;

public class PersonListResponse {

    private List<PersonDTO> persons;

    public PersonListResponse(List<PersonDTO> persons) {
        this.persons = persons;
    }

    public List<PersonDTO> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonDTO> persons) {
        this.persons = persons;
    }
}
