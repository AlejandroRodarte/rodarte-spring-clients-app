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
    private RawClientDto client;

    public BillDto(Long id, String description, String comment, Date createdAt, List<BillItemDto> billItems, RawClientDto client) {
        this.id = id;
        this.description = description;
        this.comment = comment;
        this.createdAt = createdAt;
        this.billItems = billItems;
        this.client = client;
    }

    public BillDto() {
    }

    public Double getTotal() {

        Double total = 0.00;

        for (BillItemDto billItem: billItems) {
            total += billItem.getPrice();
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

    public RawClientDto getClient() {
        return client;
    }

    public void setClient(RawClientDto client) {
        this.client = client;
    }

}
