package com.worldcupticket.msusers.service;

import com.worldcupticket.msusers.dto.AuthResponse;
import com.worldcupticket.msusers.dto.LoginRequest;

/**
 * Authentication service interface.
 *
 * Defines authentication operations and JWT token generation
 */
public interface AuthService {

    /**
     * Authenticate a user and return a JWT token
     */
    AuthResponse login(LoginRequest loginRequest);

    /**
     * Validate a JWT token
     */
    boolean validateToken(String token);

    /**
     * Extract user email from JWT token
     */
    String getEmailFromToken(String token);

}