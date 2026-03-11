package com.fakeperson.dto;

public class NameGenderDobDto {
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;

    public NameGenderDobDto(String firstName, String lastName, String gender, String dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getGender() { return gender; }
    public String getDateOfBirth() { return dateOfBirth; }
}
