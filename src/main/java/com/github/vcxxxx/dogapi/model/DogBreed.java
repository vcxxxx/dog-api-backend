package com.github.vcxxxx.dogapi.model;

import jakarta.persistence.*;
import lombok.*;

/** Entity representing a dog breed and optionally its sub-breed. */
@Entity
@Table(
    name = "dog_breeds",
    uniqueConstraints = @UniqueConstraint(columnNames = {"breed", "sub_breed"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DogBreed {

  /** Auto-generated primary key ID. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /** The main dog breed name. Cannot be null. */
  @Column(nullable = false)
  private String breed;

  /** Optional sub-breed name. Can be null. */
  @Column(nullable = true)
  private String subBreed;

  public DogBreed(String breed, String subBreed) {
    this.breed = breed;
    this.subBreed = subBreed;
  }
}
