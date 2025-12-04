package com.petstore.model;

import lombok.Builder;

@Builder
public record Tag(Long id, String name) {}