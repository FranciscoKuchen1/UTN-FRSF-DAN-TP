package com.worldcupticket.msusuarios.exception;

/**
 * Excepción lanzada cuando se intenta registrar un usuario con un email que ya existe.
 * 
 * Se retorna con código HTTP 409 Conflict
 */
public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("El email " + email + " ya está registrado en el sistema");
    }

    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
