package com.worldcupticket.msusers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for paginated admin user listing.
 * 
 * Extended version of UserProfileDTO with additional admin fields.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private Boolean enabled;
    private String createdAt;
    private String updatedAt;

}
