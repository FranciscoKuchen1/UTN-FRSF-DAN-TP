package com.worldcupticket.msusuarios.repository;

import com.worldcupticket.msusuarios.entity.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio de UsuarioRol.
 * 
 * Proporciona operaciones para la relación Usuario-Rol
 */
@Repository
public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {

    List<UsuarioRol> findByUsuarioId(Long usuarioId);

    void deleteByUsuarioIdAndRolId(Long usuarioId, Long rolId);

    boolean existsByUsuarioIdAndRolId(Long usuarioId, Long rolId);

}