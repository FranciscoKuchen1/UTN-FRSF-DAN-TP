package com.worldcupticket.msusuarios.controller;

import com.worldcupticket.msusuarios.dto.AuthResponseDTO;
import com.worldcupticket.msusuarios.dto.RegisterRequestDTO;
import com.worldcupticket.msusuarios.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de Autenticación.
 * 
 * Proporciona endpoints para autenticación de usuarios (login, registro, etc.)
 */
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Registra un nuevo usuario en la plataforma.
     * 
     * @param registerRequest datos del registro
     * @return AuthResponseDTO con token JWT y datos del usuario
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO registerRequest) {
        
        log.info("Solicitud de registro para email: {}", registerRequest.getEmail());
        AuthResponseDTO response = authService.register(registerRequest);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
