package com.fakeperson.dto;

public class NameGenderDto {
    private String firstName;
    private String lastName;
    private String gender;

    public NameGenderDto(String firstName, String lastName, String gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getGender() { return gender; }
}
