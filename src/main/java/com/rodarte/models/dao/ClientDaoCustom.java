package com.rodarte.models.dao;

import com.rodarte.models.entity.Client;

import java.util.Optional;

public interface ClientDaoCustom {
    Optional<Client> findByIdAndGetRegionsAndBills(Long id);
}
