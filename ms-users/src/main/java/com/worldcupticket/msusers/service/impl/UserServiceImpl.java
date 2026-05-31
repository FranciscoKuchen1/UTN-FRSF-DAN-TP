package com.worldcupticket.msusers.service.impl;

import com.worldcupticket.msusers.dto.UserDTO;
import com.worldcupticket.msusers.entity.User;
import com.worldcupticket.msusers.mapper.UserMapper;
import com.worldcupticket.msusers.repository.UserRepository;
import com.worldcupticket.msusers.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * User service implementation.
 *
 * Contains business logic for User operations
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> getById(Long id) {
        return userRepository.findById(id)
            .map(userMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDTO> getByEmail(String email) {
        return userRepository.findByEmail(email)
            .map(userMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAll() {
        return userRepository.findAll()
            .stream()
            .map(userMapper::toDTO)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}