package com.worldcupticket.msusers.service;

import com.worldcupticket.msusers.dto.RoleDTO;

import java.util.List;
import java.util.Optional;

/**
 * Role service interface.
 * 
 * Defines business logic operations for Role
 */
public interface RoleService {

    /**
     * Gets a role by ID
     */
    Optional<RoleDTO> getById(Long id);

    /**
     * Gets a role by name
     */
    Optional<RoleDTO> getByName(String name);

    /**
     * Gets all roles
     */
    List<RoleDTO> getAll();

}
