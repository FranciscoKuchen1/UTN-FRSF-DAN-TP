package com.worldcupticket.msusers.repository;

import com.worldcupticket.msusers.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * User repository.
 *
 * Provides CRUD operations and custom queries for User
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndActive(String email, Boolean active);

    boolean existsByEmail(String email);

}