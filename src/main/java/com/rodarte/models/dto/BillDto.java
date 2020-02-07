package com.rodarte.models.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BillDto extends RawBillDto implements Serializable {

    private List<BillItemDto> billItems;
    private RawClientDto client;

    public BillDto(Long id, String description, String comment, Date createdAt, Double total, List<BillItemDto> billItems, RawClientDto client) {
        super(id, description, comment, createdAt, total);
        this.billItems = billItems;
        this.client = client;
    }

    public BillDto() {
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
