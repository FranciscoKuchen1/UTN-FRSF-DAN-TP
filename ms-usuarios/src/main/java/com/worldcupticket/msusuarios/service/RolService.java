package com.worldcupticket.msusuarios.service;

import com.worldcupticket.msusuarios.entity.Rol;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio de Rol.
 * 
 * Define las operaciones de negocio para Roles
 */
public interface RolService {

    /**
     * Obtiene un rol por su ID
     */
    Optional<Rol> obtenerPorId(Long id);

    /**
     * Obtiene un rol por su nombre
     */
    Optional<Rol> obtenerPorNombre(String nombre);

    /**
     * Obtiene todos los roles
     */
    List<Rol> obtenerTodos();

}