package com.alurachallenge.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private Integer birthYear;
    private Integer deathYear;

    @ManyToMany(mappedBy = "authors")
    private List<Book> authoredBooks = new ArrayList<>();

    @ManyToMany(mappedBy = "translators")
    private List<Book> translatedBooks = new ArrayList<>();

    public Author() {
    }

    public Author(String name) {
        this.name = name.trim();
    }

    public Author(DataAuthor dataAuthor) {
        this.name = dataAuthor.name();
        this.birthYear = dataAuthor.birthYear();
        this.deathYear = dataAuthor.deathYear();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public List<Book> getAuthoredBooks() {
        return authoredBooks;
    }

    public void setAuthoredBooks(List<Book> authoredBooks) {
        this.authoredBooks = authoredBooks;
    }

    public List<Book> getTranslatedBooks() {
        return translatedBooks;
    }

    public void setTranslatedBooks(List<Book> translatedBooks) {
        this.translatedBooks = translatedBooks;
    }

    // equals y hashCode basados en name (único)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Author)) return false;
        Author author = (Author) o;
        return name != null && name.equalsIgnoreCase(author.name);
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        return "Author{id=" + id + ", name='" + name + "'}";
    }
}
