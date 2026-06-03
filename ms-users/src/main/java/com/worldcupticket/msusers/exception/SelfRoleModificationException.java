package com.worldcupticket.msusers.exception;

/**
 * Exception thrown when an admin tries to modify their own role.
 * 
 * Returns HTTP 400 Bad Request
 */
public class SelfRoleModificationException extends RuntimeException {

    public SelfRoleModificationException() {
        super("Admins cannot modify their own role");
    }

    public SelfRoleModificationException(String message) {
        super(message);
    }

    public SelfRoleModificationException(String message, Throwable cause) {
        super(message, cause);
    }

}
