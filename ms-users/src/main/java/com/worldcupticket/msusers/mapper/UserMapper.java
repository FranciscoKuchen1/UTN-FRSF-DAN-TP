package com.worldcupticket.msusers.mapper;

import com.worldcupticket.msusers.dto.UserDTO;
import com.worldcupticket.msusers.entity.User;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert between User (Entity) and UserDTO.
 * 
 * Performs conversions between entity and data transfer layers
 */
@Component
public class UserMapper {

    /**
     * Converts a User entity to DTO
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
            .active(user.getActive())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();
    }

    /**
     * Converts a DTO to User entity
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
            .active(dto.getActive())
            .createdAt(dto.getCreatedAt())
            .updatedAt(dto.getUpdatedAt())
            .build();
    }

}
