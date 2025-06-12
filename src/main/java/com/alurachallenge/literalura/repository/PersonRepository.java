package com.alurachallenge.literalura.repository;

import com.alurachallenge.literalura.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByNameIgnoreCase(String name);

    List<Person> findByAuthoredBooksIsNotEmpty();

    @Query("""
                SELECT DISTINCT p FROM Person p
                JOIN FETCH p.authoredBooks b
                WHERE p.birthYear <= :year
                  AND (p.deathYear IS NULL OR p.deathYear >= :year)
            """)
    List<Person> findAuthorsAliveInYearWithBooks(int year);
}
