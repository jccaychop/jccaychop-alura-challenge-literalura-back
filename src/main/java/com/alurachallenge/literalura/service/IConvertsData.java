package com.alurachallenge.literalura.service;

public interface IConvertsData {
    <T> T getData(String json, Class<T> clase);
}
