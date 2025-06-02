package com.alurachallenge.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertsData implements IConvertsData {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
