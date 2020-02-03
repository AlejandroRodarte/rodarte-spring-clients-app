package com.rodarte.models.dao;

import com.rodarte.models.entity.Client;
import com.rodarte.models.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientDao extends JpaRepository<Client, Long> {
    @Query("from Region")
    List<Region> findAllRegions();
}
