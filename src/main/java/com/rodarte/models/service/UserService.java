package com.rodarte.models.service;

import com.rodarte.models.entity.User;

public interface UserService {
    User findByUsername(String username);
}
