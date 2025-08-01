package com.github.vcxxxx.dogapi.service;

import com.github.vcxxxx.dogapi.exception.DogBreedNotFoundException;
import com.github.vcxxxx.dogapi.exception.DuplicateDogBreedException;
import com.github.vcxxxx.dogapi.model.DogBreed;
import com.github.vcxxxx.dogapi.repository.DogBreedRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer for managing DogBreed entities. Provides methods for CRUD operations and business
 * logic. Throws exceptions when resources are not found.
 */
@Service
public class DogBreedService {

  private final DogBreedRepository dogBreedRepository;

  @Autowired
  public DogBreedService(DogBreedRepository dogBreedRepository) {
    this.dogBreedRepository = dogBreedRepository;
  }

  /**
   * Retrieve all dog breeds.
   *
   * @return list of all DogBreed entities
   */
  public List<DogBreed> getAllBreeds() {
    return dogBreedRepository.findAll();
  }

  /**
   * Retrieve a dog breed by its ID.
   *
   * @param id the dog breed ID
   * @return the DogBreed if found
   * @throws DogBreedNotFoundException if not found
   */
  public DogBreed getBreedById(Long id) {
    return dogBreedRepository.findById(id).orElseThrow(() -> new DogBreedNotFoundException(id));
  }

  /**
   * Creates a new {@link DogBreed} entry if one with the same breed and sub-breed does not already
   * exist.
   *
   * <p>This method is idempotent. If a {@code DogBreed} with the same breed and sub-breed
   * combination already exists in the database, it returns the existing entry instead of inserting
   * a duplicate.
   *
   * @param dogBreed the {@code DogBreed} entity to create
   * @return the existing or newly saved {@code DogBreed} entity
   */
  @Transactional
  public DogBreed createBreed(DogBreed dogBreed) {
    return dogBreedRepository
        .findByBreedAndSubBreed(dogBreed.getBreed(), dogBreed.getSubBreed())
        .orElseGet(() -> dogBreedRepository.save(dogBreed));
  }

  /**
   * Idempotently updates the breed and sub-breed of an existing {@link DogBreed} by ID.
   *
   * <p>If the provided values are identical to the current ones, no update is performed and the
   * existing entity is returned. If another {@code DogBreed} with the same breed and sub-breed
   * already exists (with a different ID), a {@link DuplicateDogBreedException} is thrown to enforce
   * uniqueness.
   *
   * @param id the ID of the {@code DogBreed} to update
   * @param updatedDogBreed the new breed and sub-breed values
   * @return the updated {@code DogBreed} entity
   * @throws DogBreedNotFoundException if no breed exists with the specified ID
   * @throws DuplicateDogBreedException if the new breed/sub-breed combination already exists
   */
  @Transactional
  public DogBreed updateBreed(Long id, DogBreed updatedDogBreed) {
    DogBreed existing =
        dogBreedRepository.findById(id).orElseThrow(() -> new DogBreedNotFoundException(id));

    String newBreed = updatedDogBreed.getBreed();
    String newSubBreed = updatedDogBreed.getSubBreed();

    if (hasNoChange(existing, updatedDogBreed)) {
      return existing;
    }

    Optional<DogBreed> conflict = dogBreedRepository.findByBreedAndSubBreed(newBreed, newSubBreed);
    if (conflict.isPresent() && !conflict.get().getId().equals(id)) {
      throw new DuplicateDogBreedException(newBreed, newSubBreed);
    }

    existing.setBreed(newBreed);
    existing.setSubBreed(newSubBreed);

    return dogBreedRepository.save(existing);
  }

  /**
   * Idempotently deletes a {@link DogBreed} by its ID if it exists.
   *
   * <p>If no entity with the given ID exists, the operation is silently ignored to ensure
   * idempotency.
   *
   * @param id the ID of the {@code DogBreed} to delete
   */
  public void deleteBreed(Long id) {
    if (dogBreedRepository.existsById(id)) {
      dogBreedRepository.deleteById(id);
    }
  }

  private boolean hasNoChange(DogBreed existing, DogBreed updated) {
    return Objects.equals(existing.getBreed(), updated.getBreed())
        && Objects.equals(existing.getSubBreed(), updated.getSubBreed());
  }
}
