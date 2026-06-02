package com.worldcupticket.msusers.service.impl;

import com.worldcupticket.msusers.dto.ChangePasswordRequestDTO;
import com.worldcupticket.msusers.dto.UpdateProfileRequestDTO;
import com.worldcupticket.msusers.dto.UserProfileDTO;
import com.worldcupticket.msusers.entity.User;
import com.worldcupticket.msusers.exception.InvalidCredentialsException;
import com.worldcupticket.msusers.exception.PasswordMismatchException;
import com.worldcupticket.msusers.repository.UserRepository;
import com.worldcupticket.msusers.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * User management service implementation.
 * 
 * Handles user profile and password management operations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @Override
    @Transactional(readOnly = true)
    public UserProfileDTO getProfile() {
        log.debug("Fetching current user profile");
        
        User user = getCurrentUserEntity();
        
        return UserProfileDTO.builder()
            .userId(user.getId())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .email(user.getEmail())
            .role("BUYER")
            .createdAt(user.getCreatedAt().format(ISO_FORMATTER))
            .build();
    }

    @Override
    public UserProfileDTO updateProfile(UpdateProfileRequestDTO updateRequest) {
        log.debug("Updating current user profile");
        
        User user = getCurrentUserEntity();
        
        // Update only non-null fields (PATCH semantics)
        if (updateRequest.getFirstName() != null && !updateRequest.getFirstName().isBlank()) {
            user.setFirstName(updateRequest.getFirstName());
            log.debug("Updated firstName for user: {}", user.getEmail());
        }
        
        if (updateRequest.getLastName() != null && !updateRequest.getLastName().isBlank()) {
            user.setLastName(updateRequest.getLastName());
            log.debug("Updated lastName for user: {}", user.getEmail());
        }
        
        user.setUpdatedAt(LocalDateTime.now());
        User updatedUser = userRepository.save(user);
        
        log.info("Profile updated for user: {}", user.getEmail());
        
        return UserProfileDTO.builder()
            .userId(updatedUser.getId())
            .firstName(updatedUser.getFirstName())
            .lastName(updatedUser.getLastName())
            .email(updatedUser.getEmail())
            .role("BUYER")
            .createdAt(updatedUser.getCreatedAt().format(ISO_FORMATTER))
            .build();
    }

    @Override
    public void changePassword(ChangePasswordRequestDTO changePasswordRequest) {
        log.debug("Attempting password change for current user");
        
        // Validate password confirmation match
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())) {
            log.warn("Password mismatch for user");
            throw new PasswordMismatchException();
        }
        
        User user = getCurrentUserEntity();
        
        // Validate current password
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            log.warn("Invalid current password for user: {}", user.getEmail());
            throw new InvalidCredentialsException();
        }
        
        // Update password
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        
        log.info("Password changed successfully for user: {}", user.getEmail());
    }

    /**
     * Gets the current authenticated user from SecurityContext.
     *
     * @return the current User entity
     * @throws IllegalStateException if no user is authenticated
     */
    private User getCurrentUserEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()) {
            log.error("No authenticated user found in SecurityContext");
            throw new IllegalStateException("No authenticated user found");
        }
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> {
                log.error("User not found in database: {}", email);
                return new IllegalStateException("User not found: " + email);
            });
        
        return user;
    }

}
