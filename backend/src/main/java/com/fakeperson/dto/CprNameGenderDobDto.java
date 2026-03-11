package com.fakeperson.dto;

public class CprNameGenderDobDto {
    private String cpr;
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;

    public CprNameGenderDobDto(String cpr, String firstName, String lastName, String gender, String dateOfBirth) {
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getCpr() { return cpr; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getGender() { return gender; }
    public String getDateOfBirth() { return dateOfBirth; }
}
