package com.rodarte.models.dto;

import java.io.Serializable;

public class RegionDto implements Serializable {

    private Long id;
    private String name;

    public RegionDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RegionDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}