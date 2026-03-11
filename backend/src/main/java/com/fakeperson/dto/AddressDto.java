package com.fakeperson.dto;

public class AddressDto {
    private String street;
    private String number;
    private String floor;
    private String door;
    private String postalCode;
    private String townName;

    public AddressDto(String street, String number, String floor, String door, String postalCode, String townName) {
        this.street = street;
        this.number = number;
        this.floor = floor;
        this.door = door;
        this.postalCode = postalCode;
        this.townName = townName;
    }

    public String getStreet() { return street; }
    public String getNumber() { return number; }
    public String getFloor() { return floor; }
    public String getDoor() { return door; }
    public String getPostalCode() { return postalCode; }
    public String getTownName() { return townName; }
}
