package com.fakeperson.dto;

public class CprNameGenderDto {
    private String cpr;
    private String firstName;
    private String lastName;
    private String gender;

    public CprNameGenderDto(String cpr, String firstName, String lastName, String gender) {
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getCpr() { return cpr; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getGender() { return gender; }
}
