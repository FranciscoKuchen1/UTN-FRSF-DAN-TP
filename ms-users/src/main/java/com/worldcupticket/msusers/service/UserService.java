package com.worldcupticket.msusers.service;

import com.worldcupticket.msusers.dto.ChangePasswordRequestDTO;
import com.worldcupticket.msusers.dto.UpdateProfileRequestDTO;
import com.worldcupticket.msusers.dto.UserProfileDTO;

/**
 * User management service interface.
 * 
 * Defines operations for user profile management.
 */
public interface UserService {

    /**
     * Gets the current user's profile.
     * 
     * @return user profile DTO
     */
    UserProfileDTO getProfile();

    /**
     * Updates the current user's profile.
     * 
     * Only non-null fields in the request are updated (PATCH semantics).
     * Email and role cannot be changed through this endpoint.
     *
     * @param updateRequest profile update data
     * @return updated user profile DTO
     */
    UserProfileDTO updateProfile(UpdateProfileRequestDTO updateRequest);

    /**
     * Changes the current user's password.
     * 
     * Validates current password and confirms new password match.
     *
     * @param changePasswordRequest password change data
     */
    void changePassword(ChangePasswordRequestDTO changePasswordRequest);

}
