package com.worldcupticket.msusuarios.service.impl;

import com.worldcupticket.msusuarios.dto.UsuarioDTO;
import com.worldcupticket.msusuarios.entity.Usuario;
import com.worldcupticket.msusuarios.mapper.UsuarioMapper;
import com.worldcupticket.msusuarios.repository.UsuarioRepository;
import com.worldcupticket.msusuarios.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de Usuario.
 * 
 * Contiene la lógica de negocio para operaciones de Usuario
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
            .map(usuarioMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UsuarioDTO> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
            .map(usuarioMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> obtenerTodos() {
        return usuarioRepository.findAll()
            .stream()
            .map(usuarioMapper::toDTO)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

}