package com.worldcupticket.msusers.mapper;

import com.worldcupticket.msusers.dto.RoleDTO;
import com.worldcupticket.msusers.entity.Role;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert between Role (Entity) and RoleDTO.
 * 
 * Performs conversions between entity and data transfer layers
 */
@Component
public class RoleMapper {

    /**
     * Converts a Role entity to DTO
     */
    public RoleDTO toDTO(Role role) {
        if (role == null) {
            return null;
        }

        return RoleDTO.builder()
            .id(role.getId())
            .name(role.getName())
            .description(role.getDescription())
            .active(role.getActive())
            .createdAt(role.getCreatedAt())
            .updatedAt(role.getUpdatedAt())
            .build();
    }

    /**
     * Converts a DTO to Role entity
     */
    public Role toEntity(RoleDTO dto) {
        if (dto == null) {
            return null;
        }

        return Role.builder()
            .id(dto.getId())
            .name(dto.getName())
            .description(dto.getDescription())
            .active(dto.getActive())
            .createdAt(dto.getCreatedAt())
            .updatedAt(dto.getUpdatedAt())
            .build();
    }

}
