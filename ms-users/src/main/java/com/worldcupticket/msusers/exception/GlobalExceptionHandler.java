package com.worldcupticket.msusers.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Global exception handler for the application.
 * 
 * Centralizes exception handling and provides consistent responses.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles EmailAlreadyExistsException.
     * Returns HTTP 409 Conflict.
     */
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<?> handleEmailAlreadyExists(
            EmailAlreadyExistsException ex,
            WebRequest request) {
        log.warn("Email already exists: {}", ex.getMessage());
        
        Map<String, Object> body = new HashMap<>();
        body.put("error", "EMAIL_ALREADY_EXISTS");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    /**
     * Handles InvalidTokenException.
     * Returns HTTP 401 Unauthorized.
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<?> handleInvalidToken(
            InvalidTokenException ex,
            WebRequest request) {
        log.warn("Invalid token: {}", ex.getMessage());
        
        Map<String, Object> body = new HashMap<>();
        body.put("error", "INVALID_TOKEN");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles InvalidCredentialsException.
     * Returns HTTP 401 Unauthorized.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> handleInvalidCredentials(
            InvalidCredentialsException ex,
            WebRequest request) {
        log.warn("Invalid credentials: {}", ex.getMessage());
        
        Map<String, Object> body = new HashMap<>();
        body.put("error", "INVALID_CREDENTIALS");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handles PasswordMismatchException.
     * Returns HTTP 400 Bad Request.
     */
    @ExceptionHandler(PasswordMismatchException.class)
    public ResponseEntity<?> handlePasswordMismatch(
            PasswordMismatchException ex,
            WebRequest request) {
        log.warn("Password mismatch: {}", ex.getMessage());
        
        Map<String, Object> body = new HashMap<>();
        body.put("error", "PASSWORD_MISMATCH");
        body.put("message", ex.getMessage());
        body.put("timestamp", LocalDateTime.now());
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles MethodArgumentNotValidException (validation errors).
     * Returns HTTP 400 Bad Request.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        log.warn("Validation failed");
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "VALIDATION_FAILED");
        body.put("message", "Validation failed");
        
        List<Map<String, String>> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("field", error.getField());
            errorMap.put("message", error.getDefaultMessage());
            errors.add(errorMap);
        });
        body.put("errors", errors);
        
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all other exceptions.
     * Returns HTTP 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(
            Exception ex,
            WebRequest request) {
        log.error("Unexpected error occurred", ex);
        
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "INTERNAL_SERVER_ERROR");
        body.put("message", "An error occurred");
        
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
