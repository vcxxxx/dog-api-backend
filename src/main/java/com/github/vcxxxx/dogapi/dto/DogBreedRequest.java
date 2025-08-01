package com.github.vcxxxx.dogapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DogBreedRequest(
    @NotBlank(message = "Breed must not be blank")
        @Pattern(
            regexp = "^[A-Za-z]+$",
            message = "Breed must be a single word containing only letters.")
        String breed,
    @Pattern(
            regexp = "^[A-Za-z]+$",
            message = "SubBreed must be a single word containing only letters.")
        String subBreed) {}
