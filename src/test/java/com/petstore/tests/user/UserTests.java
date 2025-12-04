package com.petstore.tests.user;

import com.petstore.config.PetstoreConfig;
import com.petstore.data.UserTestData;
import com.petstore.model.ApiResponse;
import com.petstore.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
@DisplayName("Тесты для сущности User")
public class UserTests {
    @Test
    @DisplayName("POST /user - создание пользователя")
    void createUser() throws IOException, InterruptedException {
        final var newUser = UserTestData.getDefaultUser();

        final var objectMapper = PetstoreConfig.getObjectMapper();
        final var jsonBody = objectMapper.writeValueAsString(newUser);

        final var request = HttpRequest.newBuilder()
                .uri(URI.create(PetstoreConfig.BASE_URL + "/user"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        final var response = PetstoreConfig.getHttpClient().send(request,
                HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Должен быть статус код 200");

        final var apiResponse = objectMapper.readValue(response.body(), ApiResponse.class);
        assertNotNull(apiResponse.message());

        final var userRequest = HttpRequest.newBuilder()
                .uri(URI.create(PetstoreConfig.BASE_URL + "/user/" + newUser.username()))
                .GET()
                .build();

        final var userResponse = PetstoreConfig.getHttpClient().send(userRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, userResponse.statusCode());

        final var createdUser = objectMapper.readValue(userResponse.body(), User.class);
        assertNotNull(createdUser);
        assertEquals(newUser.username(), createdUser.username(), "Username должены быть равны!");
        assertEquals(createdUser.id(), Long.parseLong(apiResponse.message()), "ID должены быть равны!");
        log.info("Был создан пользователь {}", createdUser);
    }

    @Test
    @DisplayName("PUT /user/{username} - обновление пользователя")
    void putUserReplace() throws Exception {
        final var username = System.currentTimeMillis();
        final var objectMapper = PetstoreConfig.getObjectMapper();

        final var userToUpdate = UserTestData.getDefaultUser(username);
        final var jsonBody = objectMapper.writeValueAsString(userToUpdate);

        final var putRequest = HttpRequest.newBuilder()
                .uri(URI.create(PetstoreConfig.BASE_URL + "/user/" + username))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        final var putResponse = PetstoreConfig.getHttpClient().send(putRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, putResponse.statusCode(), "PUT статус 200");

        final var getRequest = HttpRequest.newBuilder()
                .uri(URI.create(PetstoreConfig.BASE_URL + "/user/" + username))
                .GET()
                .build();

        final var getResponse = PetstoreConfig.getHttpClient().send(getRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, getResponse.statusCode(), "GET статус 200");

        final var updatedUser = objectMapper.readValue(getResponse.body(), User.class);

        assertEquals(username, updatedUser.username(), "username совпадает");
        assertEquals("kek", updatedUser.firstName(), "firstName из TestData");
        assertEquals("kek.mem@example.com", updatedUser.email(), "email из TestData");

        log.info("PUT /user/{} обновил пользователя: {}", username, updatedUser.firstName());
    }

    @Test
    @DisplayName("DELETE /user/{username} - удаление пользователя")
    void deleteUser() throws Exception {
        final var newUser = UserTestData.getDefaultUser();
        final var objectMapper = PetstoreConfig.getObjectMapper();

        final var createRequest = HttpRequest.newBuilder()
                .uri(URI.create(PetstoreConfig.BASE_URL + "/user"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(newUser)))
                .build();
        final var createResponse = PetstoreConfig.getHttpClient().send(createRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, createResponse.statusCode());

        final var deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create(PetstoreConfig.BASE_URL + "/user/" + newUser.username()))
                .DELETE()
                .build();
        final var deleteResponse = PetstoreConfig.getHttpClient().send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, deleteResponse.statusCode());
        log.info("Пользователь удален: {}", newUser.username());
    }
}
