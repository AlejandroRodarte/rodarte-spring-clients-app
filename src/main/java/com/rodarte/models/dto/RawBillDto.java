package com.rodarte.models.dto;

import java.io.Serializable;
import java.util.Date;

public class RawBillDto implements Serializable {

    private Long id;
    private String description;
    private String comment;
    private Date createdAt;
    private Double total;

    public RawBillDto(Long id, String description, String comment, Date createdAt, Double total) {
        this.id = id;
        this.description = description;
        this.comment = comment;
        this.createdAt = createdAt;
        this.total = total;
    }

    public RawBillDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}
