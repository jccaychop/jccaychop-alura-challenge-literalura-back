package com.alurachallenge.literalura.util;

public final class AppConstants {
    private AppConstants() {
        // Clase de utilidades: evita instanciación
    }

    // === Endpoints Gutendex ===
    public static final String ENDPOINT_SEARCH_BY_TITLE = "/books/?search=";

    // === Códigos de mensajes API ===
    public static final String CODE_SUCCESS = "SUCCESS";
    public static final String CODE_ERROR = "ERROR";
    public static final String CODE_INSERTED = "INSERTED";
    public static final String CODE_DUPLICATES = "DUPLICATES";
    public static final String CODE_NO_RESULTS = "NO_RESULTS";

    // === Textos de mensajes API ===
    public static final String TEXT_SEARCH_COMPLETED = "Búsqueda completada.";
    public static final String TEXT_CODE_INSERTED = " libros insertados.";
    public static final String TEXT_CODE_DUPLICATES = " libros duplicados omitidos.";
    public static final String TEXT_NO_RESULTS = "No se encontraron resultados para la búsqueda.";
    public static final String TEXT_NO_RESULTS_LIST = "No se encontraron registros.";
    public static final String TEXT_RESULTS_LIST = " registros encontrados.";
}
