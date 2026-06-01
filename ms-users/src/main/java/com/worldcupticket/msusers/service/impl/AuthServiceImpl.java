package com.worldcupticket.msusers.service.impl;

import com.worldcupticket.msusers.config.JwtConfig;
import com.worldcupticket.msusers.dto.AuthResponseDTO;
import com.worldcupticket.msusers.dto.LoginRequestDTO;
import com.worldcupticket.msusers.dto.RegisterRequestDTO;
import com.worldcupticket.msusers.entity.User;
import com.worldcupticket.msusers.exception.AccountDisabledException;
import com.worldcupticket.msusers.exception.EmailAlreadyExistsException;
import com.worldcupticket.msusers.exception.InvalidCredentialsException;
import com.worldcupticket.msusers.repository.UserRepository;
import com.worldcupticket.msusers.security.JwtUtil;
import com.worldcupticket.msusers.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Authentication service implementation.
 * 
 * Handles user authentication and JWT token generation
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        log.info("Starting user registration: {}", registerRequest.getEmail());
        
        // Validate email doesn't exist
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            log.warn("Registration attempt with existing email: {}", registerRequest.getEmail());
            throw new EmailAlreadyExistsException(registerRequest.getEmail());
        }
        
        // Create new user
        User user = User.builder()
            .email(registerRequest.getEmail())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .firstName(registerRequest.getFirstName())
            .lastName(registerRequest.getLastName())
            .active(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
        
        // Save user
        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {} (ID: {})", user.getEmail(), savedUser.getId());
        
        // Generate JWT token
        String token = jwtUtil.generateToken(savedUser);
        Long expiresIn = jwtConfig.getExpiration().getMs();
        
        // Return response
        return AuthResponseDTO.builder()
            .token(token)
            .userId(UUID.randomUUID())
            .email(savedUser.getEmail())
            .firstName(savedUser.getFirstName())
            .role("BUYER")
            .expiresIn(expiresIn)
            .build();
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        log.info("Login attempt for email: {}", loginRequest.getEmail());
        
        // Find user by email
        User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> {
                log.warn("Login attempt with non-existent email: {}", loginRequest.getEmail());
                return new InvalidCredentialsException();
            });
        
        // Verify password
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            log.warn("Login attempt with incorrect password for email: {}", loginRequest.getEmail());
            throw new InvalidCredentialsException();
        }
        
        // Check if user is active
        if (!user.getActive()) {
            log.warn("Login attempt for disabled account: {}", loginRequest.getEmail());
            throw new AccountDisabledException();
        }
        
        log.info("User authenticated successfully: {}", loginRequest.getEmail());
        
        // Generate JWT token
        String token = jwtUtil.generateToken(user);
        Long expiresIn = jwtConfig.getExpiration().getMs();
        
        // Return response
        return AuthResponseDTO.builder()
            .token(token)
            .userId(UUID.randomUUID())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .role("BUYER")
            .expiresIn(expiresIn)
            .build();
    }

    @Override
    public boolean validateToken(String token) {
        return jwtUtil.isTokenValid(token);
    }

    @Override
    public String extractEmailFromToken(String token) {
        return jwtUtil.extractEmail(token);
    }

}
