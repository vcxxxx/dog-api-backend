package com.github.vcxxxx.dogapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a key-value pair used to store application metadata.
 *
 * <p>This entity is used for persisting configuration or state data that needs to survive
 * application restarts, such as flags indicating if initial data load has occurred.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Metadata {

  /** Unique key identifying the entry. */
  @Id private String metaKey;

  /** The value associated with the key. */
  private String metaValue;
}
