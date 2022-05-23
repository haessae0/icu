package com.icu.service;

import com.icu.domain.user.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface CustomUserDetailsService {

    public UserDetails loadUserByUsername(final String username);

    public CustomUserDetails createUser(User user);

}