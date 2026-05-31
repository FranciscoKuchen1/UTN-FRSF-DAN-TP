package com.worldcupticket.msusers.exception;

/**
 * Exception thrown when attempting to register a user with an email that already exists.
 * 
 * Returns HTTP 409 Conflict status
 */
public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Email " + email + " is already registered in the system");
    }

    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
