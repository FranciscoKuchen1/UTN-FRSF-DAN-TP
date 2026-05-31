package com.worldcupticket.msusers.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.worldcupticket.msusers.entity.User;
import com.worldcupticket.msusers.exception.InvalidTokenException;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

/**
 * JWT utility for token generation and validation.
 * 
 * Handles creation, parsing and validation of JWT tokens
 * using HMAC-SHA256 and standard claims.
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
     * Validates that the secret key has at least 256 bits (32 bytes).
     * Executes after properties are injected.
     */
    @PostConstruct
    public void validateSecretKey() {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(secretKeyString);
            
            if (decodedKey.length < 32) {
                String message = String.format(
                    "JWT secret key must have at least 256 bits (32 bytes). " +
                    "Current: %d bytes. Configure jwt.secret with a valid Base64 key in application.yml.",
                    decodedKey.length
                );
                log.error(message);
                throw new IllegalArgumentException(message);
            }
            
            this.secretKey = Keys.hmacShaKeyFor(decodedKey);
            log.info("JWT key validated successfully. Bits: {}", decodedKey.length * 8);
            
        } catch (IllegalArgumentException e) {
            String message = "Error decoding JWT key. Ensure it is valid Base64.";
            log.error(message, e);
            throw new IllegalArgumentException(message, e);
        }
    }

    /**
     * Generates a JWT token for a user.
     *
     * @param user the user for which to generate the token
     * @return signed JWT token
     */
    public String generateToken(User user) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        String token = Jwts.builder()
            .subject(user.getId().toString())
            .claim("email", user.getEmail())
            .claim("role", "BUYER")
            .issuedAt(now)
            .expiration(expiryDate)
            .signWith(secretKey)
            .compact();

        log.debug("JWT token generated for user: {}", user.getEmail());
        return token;
    }

    /**
     * Extracts all claims from a JWT token.
     *
     * @param token the JWT token
     * @return token claims
     * @throws InvalidTokenException if token is invalid, expired or malformed
     */
    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (JwtException e) {
            log.warn("Invalid JWT token: {}", e.getMessage());
            throw new InvalidTokenException("Invalid or expired JWT token: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            log.warn("Malformed JWT token: {}", e.getMessage());
            throw new InvalidTokenException("Malformed JWT token: " + e.getMessage(), e);
        }
    }

    /**
     * Extracts the user ID (subject) from the JWT token.
     *
     * @param token the JWT token
     * @return user ID
     * @throws InvalidTokenException if token is invalid
     */
    public String extractUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extracts the email from the JWT token.
     *
     * @param token the JWT token
     * @return user email
     * @throws InvalidTokenException if token is invalid
     */
    public String extractEmail(String token) {
        return extractAllClaims(token).get("email", String.class);
    }

    /**
     * Extracts the role from the JWT token.
     *
     * @param token the JWT token
     * @return user role
     * @throws InvalidTokenException if token is invalid
     */
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    /**
     * Validates if a JWT token is valid.
     * Does not throw exceptions, returns false if invalid or expired.
     *
     * @param token the JWT token
     * @return true if token is valid, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            log.debug("Invalid token: {}", e.getMessage());
            return false;
        }
    }

}
