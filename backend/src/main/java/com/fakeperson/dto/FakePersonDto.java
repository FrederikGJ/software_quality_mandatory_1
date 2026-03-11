package com.fakeperson.dto;

public class FakePersonDto {
    private String cpr;
    private String firstName;
    private String lastName;
    private String gender;
    private String dateOfBirth;
    private AddressDto address;
    private String phoneNumber;

    public FakePersonDto(String cpr, String firstName, String lastName, String gender,
                         String dateOfBirth, AddressDto address, String phoneNumber) {
        this.cpr = cpr;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getCpr() { return cpr; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getGender() { return gender; }
    public String getDateOfBirth() { return dateOfBirth; }
    public AddressDto getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
}
