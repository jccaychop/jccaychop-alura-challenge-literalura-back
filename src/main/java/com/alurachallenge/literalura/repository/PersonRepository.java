package com.alurachallenge.literalura.repository;

import com.alurachallenge.literalura.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByNameIgnoreCase(String name);

    List<Person> findByAuthoredBooksIsNotEmpty();
}
