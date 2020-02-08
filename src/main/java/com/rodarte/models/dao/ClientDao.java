package com.rodarte.models.dao;

import com.rodarte.models.entity.Client;
import com.rodarte.models.entity.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientDao extends JpaRepository<Client, Long>, ClientDaoCustom {

    @EntityGraph(attributePaths = "region")
    List<Client> findAll();

    @EntityGraph(attributePaths = "region")
    Page<Client> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "region")
    Optional<Client> findById(Long id);

    @Query("FROM Region")
    List<Region> findAllRegions();

}
