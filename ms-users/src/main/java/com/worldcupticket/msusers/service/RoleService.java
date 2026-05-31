package com.worldcupticket.msusers.service;

import com.worldcupticket.msusers.entity.Role;

import java.util.List;
import java.util.Optional;

/**
 * Role service interface.
 *
 * Defines business operations for Roles
 */
public interface RoleService {

    /**
     * Get a role by ID
     */
    Optional<Role> getById(Long id);

    /**
     * Get a role by name
     */
    Optional<Role> getByName(String name);

    /**
     * Get all roles
     */
    List<Role> getAll();

}