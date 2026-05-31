package com.worldcupticket.msusers.repository;

import com.worldcupticket.msusers.entity.User;
import com.worldcupticket.msusers.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * User repository.
 *
 * Provides CRUD operations and custom queries for User entities.
 * Spring Data JPA automatically implements these methods.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Find a user by email address
     *
     * @param email the user's email
     * @return an Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Find a user by email and enabled status
     *
     * @param email the user's email
     * @param enabled whether the user should be enabled
     * @return an Optional containing the user if found
     */
    Optional<User> findByEmailAndEnabled(String email, Boolean enabled);

    /**
     * Check if a user with the given email exists
     *
     * @param email the user's email
     * @return true if a user with this email exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find all users with a specific role
     *
     * @param role the user role to search for
     * @return a list of users with the specified role
     */
    List<User> findByRole(UserRole role);

    /**
     * Find all enabled users
     *
     * @return a list of all enabled users
     */
    List<User> findByEnabledTrue();

    /**
     * Find all disabled users
     *
     * @return a list of all disabled users
     */
    List<User> findByEnabledFalse();

    /**
     * Find all users with a specific role that are enabled
     *
     * @param role the user role to search for
     * @param enabled whether the users should be enabled
     * @return a list of users with the specified role and enabled status
     */
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.enabled = :enabled")
    List<User> findByRoleAndEnabled(@Param("role") UserRole role, @Param("enabled") Boolean enabled);
}