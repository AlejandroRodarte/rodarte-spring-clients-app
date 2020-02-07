package com.rodarte.models.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ClientDto extends RawClientDto implements Serializable {

    private List<RawBillDto> bills;

    public ClientDto(Long id, String firstName, String lastName, String email, Date createdAt, String image, RegionDto region, List<RawBillDto> bills) {
        super(id, firstName, lastName, email, createdAt, image, region);
        this.bills = bills;
    }

    public ClientDto() {
    }

    public List<RawBillDto> getBills() {
        return bills;
    }

    public void setBills(List<RawBillDto> bills) {
        this.bills = bills;
    }

}
