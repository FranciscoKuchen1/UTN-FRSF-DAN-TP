package com.worldcupticket.msusers.service;

import com.worldcupticket.msusers.dto.AuthResponseDTO;
import com.worldcupticket.msusers.dto.LoginRequestDTO;
import com.worldcupticket.msusers.dto.RegisterRequestDTO;

/**
 * Authentication service interface.
 * 
 * Defines authentication operations and JWT token generation
 */
public interface AuthService {

    /**
     * Registers a new user and returns a JWT token
     */
    AuthResponseDTO register(RegisterRequestDTO registerRequest);

    /**
     * Authenticates a user and returns a JWT token
     */
    AuthResponseDTO login(LoginRequestDTO loginRequest);

    /**
     * Validates a JWT token
     */
    boolean validateToken(String token);

    /**
     * Extracts the user email from a JWT token
     */
    String extractEmailFromToken(String token);

}
