package com.worldcupticket.msusers.mapper;

import com.worldcupticket.msusers.dto.UserDTO;
import com.worldcupticket.msusers.entity.User;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between User (Entity) and UserDTO.
 *
 * Performs conversions between entity and data transfer object layers.
 * Ensures sensitive information like password is not exposed in DTOs.
 */
@Component
public class UserMapper {

    /**
     * Convert a User entity to UserDTO
     *
     * @param user the User entity
     * @return the corresponding UserDTO (null if input is null)
     */
    public UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
            .id(user.getId())
            .email(user.getEmail())
            .firstName(user.getFirstName())
            .lastName(user.getLastName())
            .role(user.getRole())
            .enabled(user.getEnabled())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }

    /**
     * Convert a UserDTO to User entity
     *
     * Note: This does not set the password. Use service layer for secure password handling.
     *
     * @param dto the UserDTO
     * @return the corresponding User entity (null if input is null)
     */
    public User toEntity(UserDTO dto) {
        if (dto == null) {
            return null;
        }

        return User.builder()
            .id(dto.getId())
            .email(dto.getEmail())
            .firstName(dto.getFirstName())
            .lastName(dto.getLastName())
            .role(dto.getRole())
            .enabled(dto.getEnabled())
            .createdAt(dto.getCreatedAt())
            .updatedAt(dto.getUpdatedAt())
            .build();
    }
}