package com.alurachallenge.literalura.api.response;

/**
 * Representa un mensaje de respuesta (código + texto) que se envía al cliente.
 */
public record ApiMessage(
        String code,
        String message
) {
}
