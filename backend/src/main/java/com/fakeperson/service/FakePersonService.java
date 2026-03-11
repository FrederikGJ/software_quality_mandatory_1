package com.fakeperson.service;

import com.fakeperson.dto.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FakePersonService {

    private final CprService cprService;
    private final NameService nameService;
    private final AddressService addressService;
    private final PhoneService phoneService;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public FakePersonService(CprService cprService, NameService nameService,
                             AddressService addressService, PhoneService phoneService) {
        this.cprService = cprService;
        this.nameService = nameService;
        this.addressService = addressService;
        this.phoneService = phoneService;
    }

    // 1. Fake CPR number
    public String generateCpr() {
        NameGenderDto name = nameService.generateRandomName();
        LocalDate birthDate = cprService.generateRandomBirthDate();
        return cprService.generateCpr(birthDate, name.getGender());
    }

    // 2. Fake name and gender
    public NameGenderDto generateNameAndGender() {
        return nameService.generateRandomName();
    }

    // 3. Fake name, gender and date of birth
    public NameGenderDobDto generateNameGenderDob() {
        NameGenderDto name = nameService.generateRandomName();
        LocalDate dob = cprService.generateRandomBirthDate();
        return new NameGenderDobDto(name.getFirstName(), name.getLastName(),
                name.getGender(), dob.format(DATE_FORMAT));
    }

    // 4. Fake CPR, name and gender
    public CprNameGenderDto generateCprNameGender() {
        NameGenderDto name = nameService.generateRandomName();
        LocalDate birthDate = cprService.generateRandomBirthDate();
        String cpr = cprService.generateCpr(birthDate, name.getGender());
        return new CprNameGenderDto(cpr, name.getFirstName(), name.getLastName(), name.getGender());
    }

    // 5. Fake CPR, name, gender and date of birth
    public CprNameGenderDobDto generateCprNameGenderDob() {
        NameGenderDto name = nameService.generateRandomName();
        LocalDate birthDate = cprService.generateRandomBirthDate();
        String cpr = cprService.generateCpr(birthDate, name.getGender());
        return new CprNameGenderDobDto(cpr, name.getFirstName(), name.getLastName(),
                name.getGender(), birthDate.format(DATE_FORMAT));
    }

    // 6. Fake address
    public AddressDto generateAddress() {
        return addressService.generateAddress();
    }

    // 7. Fake phone number
    public String generatePhoneNumber() {
        return phoneService.generatePhoneNumber();
    }

    // 8. All fake person info
    public FakePersonDto generateFullPerson() {
        NameGenderDto name = nameService.generateRandomName();
        LocalDate birthDate = cprService.generateRandomBirthDate();
        String cpr = cprService.generateCpr(birthDate, name.getGender());
        AddressDto address = addressService.generateAddress();
        String phone = phoneService.generatePhoneNumber();

        return new FakePersonDto(cpr, name.getFirstName(), name.getLastName(),
                name.getGender(), birthDate.format(DATE_FORMAT), address, phone);
    }

    // 9. Bulk fake persons (2-100)
    public List<FakePersonDto> generateBulkPersons(int count) {
        if (count < 2 || count > 100) {
            throw new IllegalArgumentException("Count must be between 2 and 100");
        }
        List<FakePersonDto> persons = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            persons.add(generateFullPerson());
        }
        return persons;
    }
}
