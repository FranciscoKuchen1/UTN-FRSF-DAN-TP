package com.worldcupticket.msusers.service;

import com.worldcupticket.msusers.dto.UserDTO;
import com.worldcupticket.msusers.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * User service interface.
 *
 * Defines business operations for Users
 */
public interface UserService {

    /**
     * Get a user by ID
     */
    Optional<UserDTO> getById(Long id);

    /**
     * Get a user by email
     */
    Optional<UserDTO> getByEmail(String email);

    /**
     * Get all users
     */
    List<UserDTO> getAll();

    /**
     * Get the User entity by ID
     */
    Optional<User> getUserById(Long id);

    /**
     * Get the User entity by email
     */
    Optional<User> getUserByEmail(String email);

}