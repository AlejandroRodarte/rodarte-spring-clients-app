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
    public Optional<Client> findByIdAndGetRegionsAndBills(Long id) {

        Client client = entityManager
                .createNamedQuery("queries.Client.findById", Client.class)
                .setParameter("id", id)
                .getSingleResult();

        System.out.println(client.getFirstName());
        System.out.println(client.getBills().get(0).getCreatedAt());
        System.out.println(client.getBills().get(0).getBillItems().get(0).getQuantity());
        System.out.println(client.getBills().get(0).getBillItems().get(0).getProduct().getCreatedAt());

        return Optional.of(client);

    }

}
