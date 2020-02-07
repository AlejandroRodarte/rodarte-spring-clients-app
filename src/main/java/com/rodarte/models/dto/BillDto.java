package com.rodarte.models.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BillDto implements Serializable {

    private Long id;
    private String description;
    private String comment;
    private Date createdAt;
    private List<BillItemDto> billItems;

    public Double getTotal() {

        Double total = 0.00;

        for (BillItemDto billItem: billItems) {
            total += billItem.getImport();
        }

        return total;

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

    public List<BillItemDto> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItemDto> billItems) {
        this.billItems = billItems;
    }

}
