package com.rodarte.models.dao;

import com.rodarte.models.entity.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientDao extends CrudRepository<Client, Long> {
}
