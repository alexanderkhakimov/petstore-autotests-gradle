package com.petstore.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Pet(
        Long id,
        Category category,
        String name,
        List<String> photoUrls,
        List<Tag> tags,
        String status
) {}
