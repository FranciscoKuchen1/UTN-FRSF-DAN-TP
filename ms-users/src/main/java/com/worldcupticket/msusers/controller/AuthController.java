package com.worldcupticket.msusers.controller;

import com.worldcupticket.msusers.dto.AuthResponseDTO;
import com.worldcupticket.msusers.dto.LoginRequestDTO;
import com.worldcupticket.msusers.dto.RegisterRequestDTO;
import com.worldcupticket.msusers.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller.
 * 
 * Provides endpoints for user authentication (login, registration, etc.)
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registers a new user on the platform.
     * 
     * @param registerRequest registration data
     * @return AuthResponseDTO with JWT token and user data
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO registerRequest) {
        
        log.info("Registration request for email: {}", registerRequest.getEmail());
        AuthResponseDTO response = authService.register(registerRequest);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Authenticates a user and returns a JWT token.
     * 
     * @param loginRequest login credentials
     * @return AuthResponseDTO with JWT token and user data
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO loginRequest) {
        
        log.info("Login request for email: {}", loginRequest.getEmail());
        AuthResponseDTO response = authService.login(loginRequest);
        
        return ResponseEntity.ok(response);
    }

}
