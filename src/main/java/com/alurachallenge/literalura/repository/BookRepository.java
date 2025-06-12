package com.alurachallenge.literalura.repository;

import com.alurachallenge.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByTitleIgnoreCase(String title);

    @Query("SELECT DISTINCT l FROM Book b JOIN b.languages l")
    List<String> findAllDistinctLanguages();

    List<Book> findAllBooksByLanguages(String language);
}
