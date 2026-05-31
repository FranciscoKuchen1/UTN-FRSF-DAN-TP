package com.worldcupticket.msusuarios.service.impl;

import com.worldcupticket.msusuarios.config.JwtConfig;
import com.worldcupticket.msusuarios.dto.AuthResponseDTO;
import com.worldcupticket.msusuarios.dto.LoginRequestDTO;
import com.worldcupticket.msusuarios.dto.RegisterRequestDTO;
import com.worldcupticket.msusuarios.entity.Usuario;
import com.worldcupticket.msusuarios.exception.EmailAlreadyExistsException;
import com.worldcupticket.msusuarios.repository.UsuarioRepository;
import com.worldcupticket.msusuarios.security.JwtUtil;
import com.worldcupticket.msusuarios.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementación del servicio de Autenticación.
 * 
 * Maneja la autenticación de usuarios y generación de tokens JWT
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;

    @Override
    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        log.info("Iniciando registro de usuario: {}", registerRequest.getEmail());
        
        // Validar que el email no exista
        if (usuarioRepository.existsByEmail(registerRequest.getEmail())) {
            log.warn("Intento de registro con email existente: {}", registerRequest.getEmail());
            throw new EmailAlreadyExistsException(registerRequest.getEmail());
        }
        
        // Crear nuevo usuario
        Usuario usuario = Usuario.builder()
            .email(registerRequest.getEmail())
            .password(passwordEncoder.encode(registerRequest.getPassword()))
            .nombre(registerRequest.getFirstName())
            .apellido(registerRequest.getLastName())
            .activo(true)
            .fechaCreacion(LocalDateTime.now())
            .fechaActualizacion(LocalDateTime.now())
            .build();
        
        // Guardar usuario
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        log.info("Usuario registrado exitosamente: {} (ID: {})", usuario.getEmail(), usuarioGuardado.getId());
        
        // Generar token JWT
        String token = jwtUtil.generateToken(usuarioGuardado);
        Long expiresIn = jwtConfig.getExpiration().getMs();
        
        // Retornar respuesta
        return AuthResponseDTO.builder()
            .token(token)
            .userId(UUID.randomUUID())
            .email(usuarioGuardado.getEmail())
            .firstName(usuarioGuardado.getNombre())
            .role("BUYER")
            .expiresIn(expiresIn)
            .build();
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequest) {
        // Implementación pendiente
        return null;
    }

    @Override
    public boolean validarToken(String token) {
        return jwtUtil.isTokenValid(token);
    }

    @Override
    public String obtenerEmailDelToken(String token) {
        return jwtUtil.extractEmail(token);
    }

}
