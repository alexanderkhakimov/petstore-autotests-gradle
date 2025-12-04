package com.petstore.tests.pet;

import com.petstore.config.PetstoreConfig;
import com.petstore.data.PetTestData;
import com.petstore.model.Pet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DisplayName("Тесты для сущности Pet")
public class PetTests {

    @Test
    @DisplayName("POST /pet - создание питомца")
    void createPet() throws Exception {
        final var newPet = PetTestData.getDefaultPet();
        final var objectMapper = PetstoreConfig.getObjectMapper();
        final var jsonBody = objectMapper.writeValueAsString(newPet);

        final var request = HttpRequest.newBuilder()
                .uri(URI.create(PetstoreConfig.BASE_URL + "/pet"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        final var response = PetstoreConfig.getHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode(), "Должен быть статус код 200");

        final var createdPet = objectMapper.readValue(response.body(), Pet.class);
        assertNotNull(createdPet.id(), "У созданного питомца должен быть ID");
        log.info("Питомец создан: {}", createdPet);
    }

    @Test
    @DisplayName("GET /pet/{id} - получение питомца")
    void getPetById() throws Exception {
        final var newPet = PetTestData.getDefaultPet();
        final var objectMapper = PetstoreConfig.getObjectMapper();
        final var createJson = objectMapper.writeValueAsString(newPet);

        final var createRequest = HttpRequest.newBuilder()
                .uri(URI.create(PetstoreConfig.BASE_URL + "/pet"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(createJson))
                .build();
        final var createResponse = PetstoreConfig.getHttpClient().send(createRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, createResponse.statusCode(), "POST статус должен быть 200");

        final var createdPet = objectMapper.readValue(createResponse.body(), Pet.class);
        final var petId = createdPet.id();
        assertNotNull(petId);

        final var getRequest = HttpRequest.newBuilder()
                .uri(URI.create(PetstoreConfig.BASE_URL + "/pet/" + petId))
                .GET()
                .build();
        final var getResponse = PetstoreConfig.getHttpClient().send(getRequest, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, getResponse.statusCode(), "GET статус должен быть 200");

        final var retrievedPet = objectMapper.readValue(getResponse.body(), Pet.class);
        assertEquals(petId, retrievedPet.id(), "ID питомца должны совпадать");
        log.info("Питомец получен: {}", retrievedPet);
    }
}
