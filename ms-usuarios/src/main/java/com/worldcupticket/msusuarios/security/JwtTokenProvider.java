package com.worldcupticket.msusuarios.security;

import com.worldcupticket.msusuarios.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Proveedor de tokens JWT.
 * 
 * Maneja la creación, validación y extracción de información de tokens JWT
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;

    /**
     * Genera un token JWT para un usuario
     */
    public String generarToken(String email) {
        // Implementación pendiente
        return null;
    }

    /**
     * Valida un token JWT
     */
    public boolean validarToken(String token) {
        // Implementación pendiente
        return false;
    }

    /**
     * Extrae el email del usuario desde el token JWT
     */
    public String obtenerEmailDelToken(String token) {
        // Implementación pendiente
        return null;
    }

    /**
     * Obtiene la clave secreta para firmar/validar tokens
     */
    private SecretKey obtenerClaveSecreta() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

}