package com.rodarte.models.dao;

import com.rodarte.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
