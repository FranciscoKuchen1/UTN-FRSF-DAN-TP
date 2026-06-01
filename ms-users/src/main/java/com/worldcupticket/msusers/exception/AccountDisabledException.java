package com.worldcupticket.msusers.exception;

/**
 * Exception thrown when a user account is disabled.
 * 
 * Returns HTTP 403 Forbidden status.
 */
public class AccountDisabledException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Account is disabled";

    public AccountDisabledException() {
        super(DEFAULT_MESSAGE);
    }

    public AccountDisabledException(String message) {
        super(message);
    }

    public AccountDisabledException(String message, Throwable cause) {
        super(message, cause);
    }

}
