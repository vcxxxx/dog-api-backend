package com.github.vcxxxx.dogapi.repository;

import com.github.vcxxxx.dogapi.model.Metadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Repository for Metadata entity. */
@Repository
public interface MetadataRepository extends JpaRepository<Metadata, String> {}
