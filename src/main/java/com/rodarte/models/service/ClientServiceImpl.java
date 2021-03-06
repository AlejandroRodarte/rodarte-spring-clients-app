package com.rodarte.models.service;

import com.rodarte.models.dao.BillDao;
import com.rodarte.models.dao.ClientDao;
import com.rodarte.models.dao.ProductDao;
import com.rodarte.models.entity.Bill;
import com.rodarte.models.entity.Client;
import com.rodarte.models.entity.Product;
import com.rodarte.models.entity.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private BillDao billDao;

    @Autowired
    private ProductDao productDao;

    @Override
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        return clientDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Client findById(Long id, Boolean eager) {
        if (!eager) {
            return clientDao.findById(id).orElse(null);
        }
        return clientDao.findByIdEager(id).orElse(null);
    }

    @Override
    @Transactional
    public Client save(Client client) {
        return clientDao.save(client);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        clientDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Region> findAllRegions() {
        return clientDao.findAllRegions();
    }

    @Override
    @Transactional(readOnly = true)
    public Bill findBillById(Long id) {
        return billDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Bill saveBill(Bill bill) {
        return billDao.save(bill);
    }

    @Override
    @Transactional
    public void deleteBillById(Long id) {
        billDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> findProductByName(String name) {
        return productDao.findByNameContainingIgnoreCase(name);
    }

}
