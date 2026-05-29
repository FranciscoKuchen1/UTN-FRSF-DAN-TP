package com.worldcupticket.msusuarios.repository;

import com.worldcupticket.msusuarios.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de Usuario.
 * 
 * Proporciona operaciones CRUD y consultas personalizadas para Usuario
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    Optional<Usuario> findByEmailAndActivo(String email, Boolean activo);

    boolean existsByEmail(String email);

}