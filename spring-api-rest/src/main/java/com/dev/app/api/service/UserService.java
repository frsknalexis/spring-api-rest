package com.dev.app.api.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.dev.app.api.model.User;

public interface UserService extends UserDetailsService {

	User findByUsername(String username);
}
