package com.worldcupticket.msusers.service.impl;

import com.worldcupticket.msusers.dto.RoleDTO;
import com.worldcupticket.msusers.mapper.RoleMapper;
import com.worldcupticket.msusers.repository.RoleRepository;
import com.worldcupticket.msusers.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Role service implementation.
 * 
 * Contains business logic for Role operations
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleDTO> getById(Long id) {
        return roleRepository.findById(id)
            .map(roleMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleDTO> getByName(String name) {
        return roleRepository.findByName(name)
            .map(roleMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleDTO> getAll() {
        return roleRepository.findAll()
            .stream()
            .map(roleMapper::toDTO)
            .toList();
    }

}
