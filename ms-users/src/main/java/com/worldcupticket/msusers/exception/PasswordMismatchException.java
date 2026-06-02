package com.worldcupticket.msusers.exception;

/**
 * Exception thrown when password confirmation does not match new password.
 * 
 * Returns HTTP 400 Bad Request
 */
public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException() {
        super("New password and confirmation password do not match");
    }

    public PasswordMismatchException(String message) {
        super(message);
    }

    public PasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

}
