package com.icu.service;

import com.icu.domain.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {
    UserDetails loadUserByUsername(final String userId);

    CustomUserDetails createUser(User user);
}
