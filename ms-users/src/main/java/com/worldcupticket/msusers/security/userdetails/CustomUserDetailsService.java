package com.worldcupticket.msusers.security.userdetails;

import com.worldcupticket.msusers.entity.User;
import com.worldcupticket.msusers.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Custom UserDetailsService implementation.
 * 
 * Loads user details from the database for Spring Security authentication.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads a user by email (username).
     *
     * @param email the user's email
     * @return UserDetails object
     * @throws UsernameNotFoundException if user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> {
                log.warn("User not found with email: {}", email);
                return new UsernameNotFoundException("User not found: " + email);
            });
        
        log.debug("Loaded user details for: {}", email);
        
        // Build UserDetails with user information
        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password(user.getPassword())
            .enabled(user.getActive())
            .accountExpired(false)
            .accountLocked(false)
            .credentialsExpired(false)
            .authorities(Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_BUYER")
            ))
            .build();
    }

}
