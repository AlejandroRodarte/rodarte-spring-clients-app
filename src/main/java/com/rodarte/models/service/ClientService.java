package com.rodarte.models.service;

import com.rodarte.models.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Page<Client> findAll(Pageable pageable);
    Client findById(Long id);
    Client save(Client client);
    void deleteById(Long id);
}
