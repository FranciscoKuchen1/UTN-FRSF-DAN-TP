package com.worldcupticket.msusers.exception;

/**
 * Exception thrown when an admin tries to disable themselves.
 * 
 * Returns HTTP 400 Bad Request
 */
public class SelfDisableException extends RuntimeException {

    public SelfDisableException() {
        super("Admins cannot disable themselves");
    }

    public SelfDisableException(String message) {
        super(message);
    }

    public SelfDisableException(String message, Throwable cause) {
        super(message, cause);
    }

}
