package com.worldcupticket.msusuarios.mapper;

import com.worldcupticket.msusuarios.dto.UsuarioDTO;
import com.worldcupticket.msusuarios.entity.Usuario;
import org.springframework.stereotype.Component;

/**
 * Mapper para convertir entre Usuario (Entity) y UsuarioDTO.
 * 
 * Realiza las conversiones entre capas de entidad y transferencia de datos
 */
@Component
public class UsuarioMapper {

    /**
     * Convierte una entidad Usuario a DTO
     */
    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return UsuarioDTO.builder()
            .id(usuario.getId())
            .email(usuario.getEmail())
            .nombre(usuario.getNombre())
            .apellido(usuario.getApellido())
            .activo(usuario.getActivo())
            .fechaCreacion(usuario.getFechaCreacion())
            .fechaActualizacion(usuario.getFechaActualizacion())
            .build();
    }

    /**
     * Convierte un DTO a entidad Usuario
     */
    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }

        return Usuario.builder()
            .id(dto.getId())
            .email(dto.getEmail())
            .nombre(dto.getNombre())
            .apellido(dto.getApellido())
            .activo(dto.getActivo())
            .fechaCreacion(dto.getFechaCreacion())
            .fechaActualizacion(dto.getFechaActualizacion())
            .build();
    }

}