package com.worldcupticket.msusers.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for change role request.
 * 
 * Contains the new role to assign to a user.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeRoleRequestDTO {

    @NotBlank(message = "Role is required")
    @Pattern(regexp = "^(BUYER|ADMIN)$", message = "Role must be BUYER or ADMIN")
    private String role;

}
