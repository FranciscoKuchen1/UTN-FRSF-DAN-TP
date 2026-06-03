package com.worldcupticket.msusers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for toggle enabled status request.
 * 
 * Contains the enabled status to set for a user.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToggleEnabledRequestDTO {

    private Boolean enabled;

}
