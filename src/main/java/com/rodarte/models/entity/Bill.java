package com.rodarte.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "bills")
public class Bill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    // ignore inverse relation in serialization
    @JsonIgnoreProperties({ "bills" })
    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_id")
    @OrderColumn(name = "bill_item_index", nullable = false)
    private List<BillItem> billItems;

    @Column(name = "bill_index", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long billIndex;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Date();
    }

    public Double getTotal() {

        Double total = 0.00;

        System.out.println(billItems.size());
        for (BillItem billItem: billItems) {
            System.out.println(billItem);
            total += billItem != null ? billItem.getPrice() : 0;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<BillItem> getBillItems() {
        return billItems;
    }

    public void setBillItems(List<BillItem> billItems) {
        this.billItems = billItems;
    }

    public Long getBillIndex() {
        return billIndex;
    }

    public void setBillIndex(Long billIndex) {
        this.billIndex = billIndex;
    }

}
