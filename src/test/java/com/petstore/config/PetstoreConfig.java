package com.petstore.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;

public class PetstoreConfig {
    public static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

    public static HttpClient getHttpClient() {
        return HTTP_CLIENT;
    }
}
