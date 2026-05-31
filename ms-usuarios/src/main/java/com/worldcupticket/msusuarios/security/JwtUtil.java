package com.worldcupticket.msusuarios.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.worldcupticket.msusuarios.entity.Usuario;
import com.worldcupticket.msusuarios.exception.InvalidTokenException;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * Utilidad para generación y validación de JWT.
 * 
 * Maneja la creación, parseo y validación de tokens JWT
 * usando HMAC-SHA256 y claims estándar.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKeyString;

    @Value("${jwt.expiration.ms}")
    private Long expirationMs;

    private SecretKey secretKey;

    /**
     * Valida que la clave secreta tenga al menos 256 bits (32 bytes).
     * Se ejecuta después de que las propiedades sean inyectadas.
     */
    @PostConstruct
    public void validateSecretKey() {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(secretKeyString);
            
            if (decodedKey.length < 32) {
                String message = String.format(
                    "La clave secreta JWT debe tener al menos 256 bits (32 bytes). " +
                    "Actual: %d bytes. Configura jwt.secret con una clave Base64 válida en application.yml.",
                    decodedKey.length
                );
                log.error(message);
                throw new IllegalArgumentException(message);
            }
            
            this.secretKey = Keys.hmacShaKeyFor(decodedKey);
            log.info("Clave JWT validada correctamente. Bits: {}", decodedKey.length * 8);
            
        } catch (IllegalArgumentException e) {
            String message = "Error al decodificar la clave JWT. Asegúrate de que sea válida en Base64.";
            log.error(message, e);
            throw new IllegalArgumentException(message, e);
        }
    }

    /**
     * Genera un token JWT para un usuario.
     *
     * @param usuario el usuario para el cual generar el token
     * @return token JWT firmado
     */
    public String generateToken(Usuario usuario) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        String token = Jwts.builder()
            .subject(usuario.getId().toString())
            .claim("email", usuario.getEmail())
            .claim("role", "BUYER")
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey)
            .compact();

        log.debug("Token JWT generado para usuario: {}", usuario.getEmail());
        return token;
    }

    /**
     * Extrae todos los claims de un token JWT.
     *
     * @param token el token JWT
     * @return claims del token
     * @throws InvalidTokenException si el token es inválido, expirado o mal formado
     */
    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (JwtException e) {
            log.warn("Token JWT inválido: {}", e.getMessage());
            throw new InvalidTokenException("Token JWT inválido o expirado: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            log.warn("Token JWT mal formado: {}", e.getMessage());
            throw new InvalidTokenException("Token JWT mal formado: " + e.getMessage(), e);
        }
    }

    /**
     * Extrae el ID del usuario (subject) del token JWT.
     *
     * @param token el token JWT
     * @return ID del usuario
     * @throws InvalidTokenException si el token es inválido
     */
    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extrae el email del token JWT.
     *
     * @param token el token JWT
     * @return email del usuario
     * @throws InvalidTokenException si el token es inválido
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    /**
     * Extrae el role del token JWT.
     *
     * @param token el token JWT
     * @return role del usuario
     * @throws InvalidTokenException si el token es inválido
     */
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    /**
     * Valida si un token JWT es válido.
     * No lanza excepciones, retorna false si es inválido o expirado.
     *
     * @param token el token JWT
     * @return true si el token es válido, false en caso contrario
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Token inválido: {}", e.getMessage());
            return false;
        }
    }

}
