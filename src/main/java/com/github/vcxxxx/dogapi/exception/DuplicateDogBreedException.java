package com.github.vcxxxx.dogapi.exception;

/** Exception thrown when a DogBreed entity is not found by ID. */
public class DuplicateDogBreedException extends RuntimeException {
  public DuplicateDogBreedException(String breed, String subBreed) {
    super("Dog with " + breed + " and " + subBreed + " already exists in the database.");
  }
}
