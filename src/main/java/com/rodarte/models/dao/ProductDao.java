package com.rodarte.models.dao;

import com.rodarte.models.entity.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductDao extends CrudRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);
}
