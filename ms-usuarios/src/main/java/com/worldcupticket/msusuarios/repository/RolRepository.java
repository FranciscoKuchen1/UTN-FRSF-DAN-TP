package com.worldcupticket.msusuarios.repository;

import com.worldcupticket.msusuarios.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio de Rol.
 * 
 * Proporciona operaciones CRUD y consultas personalizadas para Rol
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {

    Optional<Rol> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}