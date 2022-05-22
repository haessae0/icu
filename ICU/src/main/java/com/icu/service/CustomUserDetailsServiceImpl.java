package com.icu.service;

import com.icu.domain.user.User;
import com.icu.domain.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class CustomUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String userId) {
        Optional<User> userEntity = userRepository.findById(userId);
        return new CustomUserDetails(userEntity.get());
    }

    public CustomUserDetails createUser(User user) {
        return new CustomUserDetails(user);
    }
}
