package com.worldcupticket.msusuarios.service;

import com.worldcupticket.msusuarios.dto.AuthResponseDTO;
import com.worldcupticket.msusuarios.dto.LoginRequestDTO;

/**
 * Interfaz del servicio de Autenticación.
 * 
 * Define las operaciones de autenticación y generación de tokens JWT
 */
public interface AuthService {

    /**
     * Autentica un usuario y retorna un token JWT
     */
    AuthResponseDTO login(LoginRequestDTO loginRequest);

    /**
     * Valida un token JWT
     */
    boolean validarToken(String token);

    /**
     * Extrae el email del usuario desde el token JWT
     */
    String obtenerEmailDelToken(String token);

}