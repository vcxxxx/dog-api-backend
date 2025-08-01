package com.github.vcxxxx.dogapi.controller;

import com.github.vcxxxx.dogapi.dto.DogBreedRequest;
import com.github.vcxxxx.dogapi.model.DogBreed;
import com.github.vcxxxx.dogapi.service.DogBreedService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** REST controller for managing DogBreed entities. */
@CrossOrigin(origins = {"http://localhost:3000", "https://myfrontend.com"})
@RestController
@RequestMapping("/api/dogbreeds")
public class DogBreedController {

  private final DogBreedService dogBreedService;

  public DogBreedController(DogBreedService dogBreedService) {
    this.dogBreedService = dogBreedService;
  }

  /**
   * Converts a {@link DogBreedRequest} DTO to a {@link DogBreed} entity, normalizing the breed and
   * sub-breed strings by trimming and converting to lowercase.
   *
   * @param request the incoming dog breed request DTO
   * @return the normalized DogBreed entity
   */
  private DogBreed toEntity(DogBreedRequest request) {
    return new DogBreed(
        normalize(request.breed()),
        request.subBreed() == null ? null : normalize(request.subBreed()));
  }

  /**
   * Normalizes a string by trimming whitespace and converting to lowercase.
   *
   * @param input the string to normalize, may be null
   * @return the normalized string or null if input was null
   */
  private String normalize(String input) {
    return input == null ? null : input.trim().toLowerCase();
  }

  /**
   * Retrieves all dog breeds.
   *
   * @return a list of all {@link DogBreed} entities wrapped in a {@link ResponseEntity}
   */
  @GetMapping
  public ResponseEntity<List<DogBreed>> getAllBreeds() {
    List<DogBreed> breeds = dogBreedService.getAllBreeds();
    return ResponseEntity.ok(breeds);
  }

  /**
   * Retrieves a specific dog breed by its ID.
   *
   * @param id the ID of the dog breed to retrieve
   * @return the {@link DogBreed} entity with the given ID wrapped in a {@link ResponseEntity}
   */
  @GetMapping("/{id}")
  public ResponseEntity<DogBreed> getBreedById(@PathVariable Long id) {
    DogBreed breed = dogBreedService.getBreedById(id);
    return ResponseEntity.ok(breed);
  }

  /**
   * Creates a new dog breed entry.
   *
   * @param request the incoming validated {@link DogBreedRequest} DTO
   * @return the created {@link DogBreed} entity wrapped in a {@link ResponseEntity} with status 201
   *     Created
   */
  @PostMapping
  public ResponseEntity<DogBreed> createBreed(@Valid @RequestBody DogBreedRequest request) {
    DogBreed created = dogBreedService.createBreed(toEntity(request));
    return ResponseEntity.status(201).body(created);
  }

  /**
   * Updates an existing dog breed identified by its ID.
   *
   * @param id the ID of the dog breed to update
   * @param request the incoming validated {@link DogBreedRequest} DTO with updated values
   * @return the updated {@link DogBreed} entity wrapped in a {@link ResponseEntity}
   */
  @PutMapping("/{id}")
  public ResponseEntity<DogBreed> updateBreed(
      @PathVariable Long id, @Valid @RequestBody DogBreedRequest request) {
    DogBreed updated = dogBreedService.updateBreed(id, toEntity(request));
    return ResponseEntity.ok(updated);
  }

  /**
   * Deletes a dog breed by its ID.
   *
   * @param id the ID of the dog breed to delete
   * @return a {@link ResponseEntity} with HTTP status 204 No Content
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteBreed(@PathVariable Long id) {
    dogBreedService.deleteBreed(id);
    return ResponseEntity.noContent().build();
  }
}
