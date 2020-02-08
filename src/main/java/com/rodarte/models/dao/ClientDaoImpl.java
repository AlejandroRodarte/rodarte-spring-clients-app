package com.rodarte.models.dao;

import com.rodarte.models.entity.Client;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class ClientDaoImpl implements ClientDaoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Client> findByIdEager(Long id) {

        Client client = entityManager
                .createNamedQuery("queries.Client.findById", Client.class)
                .setParameter("id", id)
                .getSingleResult();

        return Optional.of(client);

    }

}
