package com.worldcupticket.msusers.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT configuration properties.
 *
 * Maps JWT properties from application.yml
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String secret;
    private Expiration expiration;

    public static class Expiration {
        private Long ms;

        public Long getMs() {
            return ms;
        }

        public void setMs(Long ms) {
            this.ms = ms;
        }
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Expiration getExpiration() {
        return expiration;
    }

    public void setExpiration(Expiration expiration) {
        this.expiration = expiration;
    }

}