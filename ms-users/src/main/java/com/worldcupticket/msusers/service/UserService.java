package com.worldcupticket.msusers.service;

import com.worldcupticket.msusers.dto.UserDTO;
import com.worldcupticket.msusers.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * User service interface.
 * 
 * Defines business logic operations for User
 */
public interface UserService {

    /**
     * Gets a user by ID as DTO
     */
    Optional<UserDTO> getById(Long id);

    /**
     * Gets a user by email as DTO
     */
    Optional<UserDTO> getByEmail(String email);

    /**
     * Gets all users as DTOs
     */
    List<UserDTO> getAll();

    /**
     * Gets a user by ID as entity
     */
    Optional<User> getUserById(Long id);

    /**
     * Gets a user by email as entity
     */
    Optional<User> getUserByEmail(String email);

}
