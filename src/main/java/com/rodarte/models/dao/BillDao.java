package com.rodarte.models.dao;

import com.rodarte.models.entity.Bill;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface BillDao extends CrudRepository<Bill, Long> {
    @EntityGraph(attributePaths = "billItems.product")
    Optional<Bill> findById(Long id);
}
