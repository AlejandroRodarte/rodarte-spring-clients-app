package com.rodarte.models.service;

import com.rodarte.models.entity.Client;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Client findById(Long id);
    Client save(Client client);
    void deleteById(Long id);
}
