package com.github.vcxxxx.dogapi.repository;

import com.github.vcxxxx.dogapi.model.DogBreed;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for DogBreed entities.
 *
 * <p>Extends JpaRepository to provide standard CRUD operations. Also includes custom finder methods
 * for querying breeds and sub-breeds.
 */
@Repository
public interface DogBreedRepository extends JpaRepository<DogBreed, Long> {

  /**
   * Finds all DogBreed entities matching the specified breed name, regardless of sub-breed.
   *
   * @param breed the main breed name to search for
   * @return list of DogBreed entities with the given breed
   */
  List<DogBreed> findByBreed(String breed);

  /**
   * Finds a DogBreed entity by its breed and sub-breed.
   *
   * @param breed the main breed name
   * @param subBreed the sub-breed name (can be null)
   * @return an {@code Optional} containing the matching DogBreed entity if found, or empty Optional
   *     if none found
   */
  Optional<DogBreed> findByBreedAndSubBreed(String breed, String subBreed);
}
