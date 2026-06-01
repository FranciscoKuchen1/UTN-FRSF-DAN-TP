package com.worldcupticket.msusers.exception;

/**
 * Exception thrown when authentication credentials are invalid.
 * 
 * Returns HTTP 401 Unauthorized status.
 * Generic message used to avoid revealing if email exists or not.
 */
public class InvalidCredentialsException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Invalid credentials";

    public InvalidCredentialsException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }

}
