package com.alurachallenge.literalura.api.response;

import com.alurachallenge.literalura.util.AppConstants;

import java.util.List;

/**
 * Envuelve la respuesta de cualquier endpoint, incluyendo:
 * - un "status" (success/error)
 * - una lista de mensajes (códigos + textos)
 * - opcionalmente, un payload de datos (data)
 */
public class ApiResponse<T> {
    private String status;            // "success" o "error"
    private List<ApiMessage> messages;
    private T data;

    private ApiResponse(String status, List<ApiMessage> messages, T data) {
        this.status = status;
        this.messages = messages;
        this.data = data;
    }

    /**
     * Construye una respuesta exitosa, con data y lista de mensajes.
     */
    public static <T> ApiResponse<T> success(T data, List<ApiMessage> messages) {
        return new ApiResponse<>(AppConstants.CODE_SUCCESS, messages, data);
    }

    /**
     * Construye una respuesta de error, con lista de mensajes y sin payload (data = null).
     */
    public static <T> ApiResponse<T> failure(List<ApiMessage> messages) {
        return new ApiResponse<>(AppConstants.CODE_ERROR, messages, null);
    }

    // --- Getters y setters (importantes para que Jackson los serialice) ---

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ApiMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ApiMessage> messages) {
        this.messages = messages;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
