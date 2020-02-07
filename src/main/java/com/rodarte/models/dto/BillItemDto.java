package com.rodarte.models.dto;

import java.io.Serializable;

public class BillItemDto implements Serializable {

    private Long id;
    private Integer quantity;
    private ProductDto product;

    public Double getPrice() {
        return this.quantity.doubleValue() * this.product.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

}
