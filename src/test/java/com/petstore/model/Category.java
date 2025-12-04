package com.petstore.model;

import lombok.Builder;

@Builder
public record Category(Long id, String name) {}
