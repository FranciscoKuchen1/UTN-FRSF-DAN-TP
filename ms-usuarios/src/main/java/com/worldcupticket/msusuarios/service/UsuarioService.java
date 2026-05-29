package com.worldcupticket.msusuarios.service;

import com.worldcupticket.msusuarios.dto.UsuarioDTO;
import com.worldcupticket.msusuarios.entity.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio de Usuario.
 * 
 * Define las operaciones de negocio para Usuarios
 */
public interface UsuarioService {

    /**
     * Obtiene un usuario por su ID
     */
    Optional<UsuarioDTO> obtenerPorId(Long id);

    /**
     * Obtiene un usuario por su email
     */
    Optional<UsuarioDTO> obtenerPorEmail(String email);

    /**
     * Obtiene todos los usuarios
     */
    List<UsuarioDTO> obtenerTodos();

    /**
     * Obtiene la entidad Usuario por ID
     */
    Optional<Usuario> obtenerUsuarioPorId(Long id);

    /**
     * Obtiene la entidad Usuario por email
     */
    Optional<Usuario> obtenerUsuarioPorEmail(String email);

}