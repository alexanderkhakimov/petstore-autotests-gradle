package com.petstore.data;

import com.petstore.model.User;

public class UserTestData {

    public static User getDefaultUser() {
        return getDefaultUser(System.currentTimeMillis());
    }

    public static User getDefaultUser(Long username) {
        return User.builder()
                .username(username)
                .firstName("kek")
                .lastName("mem")
                .email("kek.mem@example.com")
                .password("password123")
                .phone("+7234567890")
                .userStatus(1)
                .build();
    }

    public static User getUpdatedUser() {
        return User.builder()
                .username(123456789L)
                .firstName("bek")
                .email("kek.updated@example.com")
                .lastName("mem")
                .password("password123")
                .phone("+7234567890")
                .userStatus(1)
                .build();
    }
}

