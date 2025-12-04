package com.petstore.model;

import lombok.Builder;

@Builder
public record User(
        Long id,
        Long username,
        String firstName,
        String lastName,
        String email,
        String password,
        String phone,
        Integer userStatus
) {}
