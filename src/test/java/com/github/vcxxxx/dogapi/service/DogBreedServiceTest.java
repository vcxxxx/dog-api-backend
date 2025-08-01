package com.github.vcxxxx.dogapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.vcxxxx.dogapi.exception.DogBreedNotFoundException;
import com.github.vcxxxx.dogapi.model.DogBreed;
import com.github.vcxxxx.dogapi.repository.DogBreedRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class DogBreedServiceTest {

  @Mock private DogBreedRepository dogBreedRepository;

  @InjectMocks private DogBreedService dogBreedService;

  private DogBreed sampleDog;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    sampleDog = new DogBreed(1L, "bulldog", "french");
  }

  @Test
  void getAllBreeds_ReturnsList() {
    when(dogBreedRepository.findAll()).thenReturn(Collections.singletonList(sampleDog));

    List<DogBreed> result = dogBreedService.getAllBreeds();

    assertEquals(1, result.size());
    assertEquals("bulldog", result.get(0).getBreed());
    verify(dogBreedRepository, times(1)).findAll();
  }

  @Test
  void getBreedById_Found_ReturnsDogBreed() {
    when(dogBreedRepository.findById(1L)).thenReturn(Optional.of(sampleDog));

    DogBreed result = dogBreedService.getBreedById(1L);

    assertEquals("bulldog", result.getBreed());
  }

  @Test
  void getBreedById_NotFound_ThrowsException() {
    when(dogBreedRepository.findById(2L)).thenReturn(Optional.empty());

    assertThrows(DogBreedNotFoundException.class, () -> dogBreedService.getBreedById(2L));
  }

  @Test
  void createBreed_SavesAndReturnsDogBreed() {
    when(dogBreedRepository.save(sampleDog)).thenReturn(sampleDog);

    DogBreed result = dogBreedService.createBreed(sampleDog);

    assertEquals(sampleDog, result);
    verify(dogBreedRepository, times(1)).save(sampleDog);
  }

  @Test
  void updateBreed_Found_UpdatesAndReturnsDogBreed() {
    DogBreed updatedDog = new DogBreed(null, "bulldog", "boston");

    when(dogBreedRepository.findById(1L)).thenReturn(Optional.of(sampleDog));
    when(dogBreedRepository.save(any(DogBreed.class)))
        .thenAnswer(invocation -> invocation.getArgument(0));

    DogBreed result = dogBreedService.updateBreed(1L, updatedDog);

    assertEquals("bulldog", result.getBreed());
    assertEquals("boston", result.getSubBreed());
  }

  @Test
  void updateBreed_NotFound_ThrowsException() {
    when(dogBreedRepository.findById(2L)).thenReturn(Optional.empty());

    DogBreed updatedDog = new DogBreed(null, "bulldog", "boston");

    assertThrows(
        DogBreedNotFoundException.class, () -> dogBreedService.updateBreed(2L, updatedDog));
  }

  @Test
  void deleteBreed_Found_DeletesSuccessfully() {
    when(dogBreedRepository.existsById(1L)).thenReturn(true);
    doNothing().when(dogBreedRepository).deleteById(1L);

    assertDoesNotThrow(() -> dogBreedService.deleteBreed(1L));
    verify(dogBreedRepository, times(1)).deleteById(1L);
  }
}
