package com.worldcupticket.msusers.dto;

import com.worldcupticket.msusers.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for User.
 *
 * Used in API responses to expose user information securely.
 * Does not expose password or sensitive data.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
    private Boolean enabled;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    /**
     * Get the user's full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
}