package com.github.vcxxxx.dogapi.exception;

/**
 * Represents a single field-level validation error.
 *
 * @param field the name of the field that failed validation
 * @param message readable validation error message
 */
public record ValidationError(String field, String message) {}
