package com.rodarte.models.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "clients")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "can not be empty")
    @Size(min = 4, max = 12, message = "must be between 4 and 12 characters")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty(message = "can not be empty")
    @Column(name = "last_name")
    private String lastName;

    @NotEmpty(message = "can not be empty")
    @Email(message = "must be a valid email")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull(message = "can not be empty")
    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
