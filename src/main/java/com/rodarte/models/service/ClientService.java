package com.rodarte.models.service;

import com.rodarte.models.entity.Bill;
import com.rodarte.models.entity.Client;
import com.rodarte.models.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {
    List<Client> findAll();
    Page<Client> findAll(Pageable pageable);
    Client findById(Long id, Boolean eager);
    Client save(Client client);
    void deleteById(Long id);
    List<Region> findAllRegions();
    Bill findBillById(Long id);
    Bill saveBill(Bill bill);
    void deleteBillById(Long id);
}
