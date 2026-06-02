package com.worldcupticket.msusers.controller;

import com.worldcupticket.msusers.dto.ChangePasswordRequestDTO;
import com.worldcupticket.msusers.dto.UpdateProfileRequestDTO;
import com.worldcupticket.msusers.dto.UserProfileDTO;
import com.worldcupticket.msusers.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * User management controller.
 * 
 * Provides endpoints for user profile and password management.
 * All endpoints require authentication (BUYER or ADMIN role).
 */
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Gets the current user's profile.
     * 
     * Endpoint: GET /api/users/me
     * Access: BUYER, ADMIN (requires valid JWT)
     *
     * @return user profile
     */
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('BUYER', 'ADMIN')")
    public ResponseEntity<UserProfileDTO> getProfile() {
        log.info("GET /api/users/me - Current user profile request");
        UserProfileDTO profile = userService.getProfile();
        return ResponseEntity.ok(profile);
    }

    /**
     * Updates the current user's profile.
     * 
     * Endpoint: PUT /api/users/me
     * Access: BUYER, ADMIN
     * 
     * Only non-null fields in the request are updated (PATCH semantics).
     * Email and role cannot be changed through this endpoint.
     *
     * @param updateRequest profile update data
     * @return updated user profile
     */
    @PutMapping("/me")
    @PreAuthorize("hasAnyRole('BUYER', 'ADMIN')")
    public ResponseEntity<UserProfileDTO> updateProfile(
            @Valid @RequestBody UpdateProfileRequestDTO updateRequest) {
        log.info("PUT /api/users/me - Profile update request");
        UserProfileDTO updatedProfile = userService.updateProfile(updateRequest);
        return ResponseEntity.ok(updatedProfile);
    }

    /**
     * Changes the current user's password.
     * 
     * Endpoint: PUT /api/users/me/password
     * Access: BUYER, ADMIN
     * 
     * Validates current password and confirms new password match.
     *
     * @param changePasswordRequest password change data
     * @return 204 No Content on success
     */
    @PutMapping("/me/password")
    @PreAuthorize("hasAnyRole('BUYER', 'ADMIN')")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequestDTO changePasswordRequest) {
        log.info("PUT /api/users/me/password - Password change request");
        userService.changePassword(changePasswordRequest);
        return ResponseEntity.noContent().build();
    }

}
