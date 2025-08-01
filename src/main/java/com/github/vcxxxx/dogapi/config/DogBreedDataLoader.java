package com.github.vcxxxx.dogapi.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vcxxxx.dogapi.model.DogBreed;
import com.github.vcxxxx.dogapi.model.Metadata;
import com.github.vcxxxx.dogapi.repository.DogBreedRepository;
import com.github.vcxxxx.dogapi.repository.MetadataRepository;
import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Component responsible for loading initial dog breed data into the database exactly once.
 *
 * <p>This class reads a JSON file containing dog breeds and their sub-breeds and persists them to
 * the database only if they have not been loaded previously. The loading status is tracked via a
 * Metadata entity to prevent duplicate data insertion on application restart.
 */
@Component
public class DogBreedDataLoader {

  private static final Logger logger = LoggerFactory.getLogger(DogBreedDataLoader.class);
  private static final String DOG_BREEDS_LOADED_KEY = "dog_breeds_loaded";

  private final DogBreedRepository dogBreedRepository;
  private final MetadataRepository metadataRepository;

  /**
   * Constructs the DogBreedDataLoader with the required repositories.
   *
   * @param dogBreedRepository repository for DogBreed entities
   * @param metadataRepository repository for Metadata entities
   */
  public DogBreedDataLoader(
      DogBreedRepository dogBreedRepository, MetadataRepository metadataRepository) {
    this.dogBreedRepository = dogBreedRepository;
    this.metadataRepository = metadataRepository;
  }

  /**
   * Loads dog breed data from a JSON file into the database if it has not been loaded before.
   *
   * <p>This method runs once on application startup and checks a metadata flag to determine if the
   * data load has already occurred. If the flag is absent, it reads the JSON file, persists the
   * data, and then sets the flag to prevent future loads.
   *
   * @throws RuntimeException if the JSON file cannot be read or data fails to load
   */
  @PostConstruct
  public void loadDataIfNotAlreadyLoaded() {
    boolean alreadyLoaded = metadataRepository.existsById(DOG_BREEDS_LOADED_KEY);
    if (alreadyLoaded) {
      logger.info("Dog breeds already loaded, skipping JSON import.");
      return;
    }

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      InputStream is = getClass().getClassLoader().getResourceAsStream("dogs.json");
      Map<String, List<String>> breedMap = objectMapper.readValue(is, new TypeReference<>() {});

      breedMap.forEach(
          (breed, subBreeds) -> {
            if (subBreeds.isEmpty()) {
              dogBreedRepository.save(new DogBreed(null, breed, null));
            } else {
              subBreeds.forEach(sub -> dogBreedRepository.save(new DogBreed(null, breed, sub)));
            }
          });

      // Save the metadata flag after successful load
      metadataRepository.save(new Metadata(DOG_BREEDS_LOADED_KEY, "true"));
      logger.info("Dog breeds loaded into database successfully.");
    } catch (Exception e) {
      logger.error("Failed to load dog breeds from JSON", e);
      throw new RuntimeException("Failed to load dog breeds", e);
    }
  }
}
