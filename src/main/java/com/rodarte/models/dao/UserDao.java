package com.rodarte.models.dao;

import com.rodarte.models.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User, Long> {
    @EntityGraph(attributePaths = "roles")
    User findByUsername(String username);
}
