package com.worldcupticket.msusers.security;

import com.worldcupticket.msusers.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * JWT token provider.
 *
 * Handles JWT token creation, validation and information extraction
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;

    /**
     * Generate a JWT token for a user
     */
    public String generateToken(String email) {
        // Implementation pending
        return null;
    }

    /**
     * Validate a JWT token
     */
    public boolean validateToken(String token) {
        // Implementation pending
        return false;
    }

    /**
     * Extract user email from JWT token
     */
    public String getEmailFromToken(String token) {
        // Implementation pending
        return null;
    }

    /**
     * Get the secret key for signing/validating tokens
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

}