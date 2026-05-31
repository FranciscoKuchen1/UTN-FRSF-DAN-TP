package com.worldcupticket.msusuarios.exception;

/**
 * Excepción lanzada cuando un token JWT es inválido, expirado o mal formado.
 */
public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String message) {
        super(message);
    }

    public InvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

}
