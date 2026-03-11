package com.fakeperson.model;

import jakarta.persistence.*;

@Entity
@Table(name = "postal_code")
public class PostalCode {

    @Id
    @Column(name = "postal_code", length = 4)
    private String postalCode;

    @Column(name = "town_name", nullable = false)
    private String townName;

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public String getTownName() { return townName; }
    public void setTownName(String townName) { this.townName = townName; }
}
