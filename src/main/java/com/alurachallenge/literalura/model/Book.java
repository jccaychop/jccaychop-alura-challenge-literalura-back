package com.alurachallenge.literalura.model;

import jakarta.persistence.*;

import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "book_summaries", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "summary", columnDefinition = "TEXT")
    private List<String> summaries = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "book_translators",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "translator_id")
    )
    private List<Author> translators = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "language")
    private List<String> languages = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "book_formats", joinColumns = @JoinColumn(name = "book_id"))
    @MapKeyColumn(name = "format_key")
    @Column(name = "format_value")
    private Map<String, String> formats;

    private Integer downloadCount;

    public Book() {
    }

    public Book(String title, List<Author> authors) {
        this.title = title;
        this.authors = authors;
    }

    public Book(DataBook dataBook) {
        this.title = dataBook.title();
        this.summaries = dataBook.summaries();
        this.languages = dataBook.languages();
        this.setFormats(dataBook.formats());
        this.downloadCount = dataBook.downloadCount();

        dataBook.authors().forEach(data -> this.addAuthor(new Author(data)));
        dataBook.translators().forEach(data -> this.addTranslator(new Author(data)));
    }

    public void addAuthor(Author author) {
        authors.add(author);
        if (!author.getAuthoredBooks().contains(this)) {
            author.getAuthoredBooks().add(this);
        }
    }

    public void addTranslator(Author translator) {
        translators.add(translator);
        if (!translator.getTranslatedBooks().contains(this)) {
            translator.getTranslatedBooks().add(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<String> getSummaries() {
        return summaries;
    }

    public void setSummaries(List<String> summaries) {
        this.summaries = summaries;
    }

    public List<Author> getTranslators() {
        return translators;
    }

    public void setTranslators(List<Author> translators) {
        this.translators = translators;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public Map<String, String> getFormats() {
        return formats;
    }

    public void setFormats(Map<String, String> formats) {
        if (formats == null) {
            this.formats = null;
            return;
        }

        // Se define un conjunto con las claves que serán aceptadas
        Set<String> allowedFormats = Set.of(
                "application/epub+zip",
                "image/jpeg"
        );

        // Se filtra el mapa para dejar solo las entradas permitidas
        this.formats = formats.entrySet().stream()
                .filter(entry -> allowedFormats.contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors.stream().map(Author::getName).toList() +
                ", translators=" + translators.stream().map(Author::getName).toList() +
                ", languages=" + languages +
                ", downloadCount=" + downloadCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book book)) return false;
        return Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
