package com.github.vcxxxx.dogapi.exception;

/** Exception thrown when a DogBreed entity is not found by ID. */
public class DogBreedNotFoundException extends RuntimeException {
  public DogBreedNotFoundException(Long id) {
    super("Dog breed with ID " + id + " not found.");
  }
}
