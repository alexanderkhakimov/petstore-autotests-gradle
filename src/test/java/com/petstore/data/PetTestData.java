package com.petstore.data;

import com.petstore.model.Category;
import com.petstore.model.Pet;
import com.petstore.model.Tag;

public class PetTestData {

    public static Pet getDefaultPet() {
        return getDefaultPet(System.currentTimeMillis());
    }

    public static Pet getDefaultPet(final Long petId) {
        return Pet.builder()
                .id(petId)
                .name("TestDog")
                .status("available")
                .category(Category.builder()
                        .id(1L)
                        .name("Dogs")
                        .build())
                .photoUrls(java.util.List.of("http://example.com/photo1.jpg"))
                .tags(java.util.List.of(
                        Tag.builder()
                                .id(1L)
                                .name("friendly")
                                .build()
                ))
                .build();
    }
}
