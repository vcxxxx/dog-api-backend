package com.github.vcxxxx.dogapi.exception;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the Dog API application.
 *
 * <p>Handles application-wide exceptions and converts them into meaningful HTTP responses.
 * Centralizes error handling for cleaner controller and service code.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles {@link DogBreedNotFoundException} thrown from anywhere in the application.
   *
   * <p>Returns a 404 Not Found response with the exception message as the response body.
   *
   * @param ex the exception thrown when a requested dog breed is not found
   * @return a {@link ResponseEntity} with status 404 and the exception message
   */
  @ExceptionHandler(DogBreedNotFoundException.class)
  public ResponseEntity<String> handleNotFound(DogBreedNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  /**
   * Handles {@link DuplicateDogBreedException} thrown when trying to update a dog breed with values
   * that already exist.
   *
   * <p>Returns a 409 Conflict response with the exception message as the response body.
   *
   * @param ex the exception thrown due to a duplicate breed/sub-breed conflict
   * @return a {@link ResponseEntity} with status 409 and the exception message
   */
  @ExceptionHandler(DuplicateDogBreedException.class)
  public ResponseEntity<String> handleDuplicate(DuplicateDogBreedException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
  }

  /**
   * Handles validation errors triggered when request body validation fails.
   *
   * <p>Extracts field-specific error messages and returns them as a list of {@link ValidationError}
   * objects with HTTP 400 (Bad Request) status.
   *
   * @param ex the exception containing details about validation failures
   * @return a {@code ResponseEntity} with status 400 and a list of validation errors
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<ValidationError>> handleValidationErrors(
      MethodArgumentNotValidException ex) {
    List<ValidationError> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());

    return ResponseEntity.badRequest().body(errors);
  }
}
