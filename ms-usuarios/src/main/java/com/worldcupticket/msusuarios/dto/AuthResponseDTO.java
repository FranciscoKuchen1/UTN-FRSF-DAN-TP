package com.worldcupticket.msusuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO para respuesta de autenticación.
 * 
 * Contiene el token JWT y información del usuario autenticado
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

    private String token;
    private UUID userId;
    private String email;
    private String firstName;
    private String role;
    private Long expiresIn;

}
