package com.worldcupticket.msusers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for user profile response.
 * 
 * Contains user profile information returned by the API.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String createdAt;

}
