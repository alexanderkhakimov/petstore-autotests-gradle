package com.petstore.model;

public record ApiResponse(
        Integer code,
        String type,
        String message
) {
}
