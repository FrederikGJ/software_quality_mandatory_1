package com.fakeperson.integration;

import com.fakeperson.dto.*;
import com.fakeperson.service.FakePersonService;
import com.fakeperson.service.PhoneService;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class FullIntegrationTest {

    @Autowired
    private FakePersonService fakePersonService;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // --- Endpoint 1: CPR ---

    @RepeatedTest(10)
    void generateCpr_shouldReturn10DigitString() {
        String cpr = fakePersonService.generateCpr();
        assertNotNull(cpr);
        assertEquals(10, cpr.length());
        assertTrue(cpr.matches("\\d{10}"), "CPR should be 10 digits: " + cpr);
    }

    @RepeatedTest(10)
    void generateCpr_datePartShouldBeValidDate() {
        String cpr = fakePersonService.generateCpr();
        String datePart = cpr.substring(0, 6);
        int day = Integer.parseInt(datePart.substring(0, 2));
        int month = Integer.parseInt(datePart.substring(2, 4));
        assertTrue(day >= 1 && day <= 31, "Day should be 1-31: " + day);
        assertTrue(month >= 1 && month <= 12, "Month should be 1-12: " + month);
    }

    // --- Endpoint 2: Name & Gender ---

    @Test
    void generateNameAndGender_shouldReturnValidData() {
        NameGenderDto result = fakePersonService.generateNameAndGender();
        assertNotNull(result);
        assertNotNull(result.getFirstName());
        assertNotNull(result.getLastName());
        assertFalse(result.getFirstName().isEmpty());
        assertFalse(result.getLastName().isEmpty());
        assertTrue(result.getGender().equals("male") || result.getGender().equals("female"),
                "Gender should be male or female: " + result.getGender());
    }

    // --- Endpoint 3: Name, Gender & DOB ---

    @Test
    void generateNameGenderDob_shouldReturnValidData() {
        NameGenderDobDto result = fakePersonService.generateNameGenderDob();
        assertNotNull(result);
        assertNotNull(result.getFirstName());
        assertNotNull(result.getLastName());
        assertNotNull(result.getGender());
        assertNotNull(result.getDateOfBirth());

        LocalDate dob = LocalDate.parse(result.getDateOfBirth(), DATE_FORMAT);
        assertTrue(dob.isBefore(LocalDate.now()), "DOB should be in the past");
    }

    // --- Endpoint 4: CPR, Name & Gender ---

    @RepeatedTest(10)
    void generateCprNameGender_cprGenderShouldMatch() {
        CprNameGenderDto result = fakePersonService.generateCprNameGender();
        assertNotNull(result);
        assertEquals(10, result.getCpr().length());

        int lastDigit = Character.getNumericValue(result.getCpr().charAt(9));
        if ("female".equals(result.getGender())) {
            assertEquals(0, lastDigit % 2, "Female CPR last digit should be even");
        } else {
            assertEquals(1, lastDigit % 2, "Male CPR last digit should be odd");
        }
    }

    // --- Endpoint 5: CPR, Name, Gender & DOB ---

    @RepeatedTest(10)
    void generateCprNameGenderDob_cprShouldMatchDob() {
        CprNameGenderDobDto result = fakePersonService.generateCprNameGenderDob();
        assertNotNull(result);

        LocalDate dob = LocalDate.parse(result.getDateOfBirth(), DATE_FORMAT);
        String expectedDatePart = dob.format(DateTimeFormatter.ofPattern("ddMMyy"));
        assertEquals(expectedDatePart, result.getCpr().substring(0, 6),
                "CPR date part should match DOB");
    }

    @RepeatedTest(10)
    void generateCprNameGenderDob_genderShouldMatchCpr() {
        CprNameGenderDobDto result = fakePersonService.generateCprNameGenderDob();
        int lastDigit = Character.getNumericValue(result.getCpr().charAt(9));
        if ("female".equals(result.getGender())) {
            assertEquals(0, lastDigit % 2);
        } else {
            assertEquals(1, lastDigit % 2);
        }
    }

    // --- Endpoint 6: Address ---

    @Test
    void generateAddress_shouldReturnCompleteAddress() {
        AddressDto address = fakePersonService.generateAddress();
        assertNotNull(address);
        assertNotNull(address.getStreet());
        assertNotNull(address.getNumber());
        assertNotNull(address.getFloor());
        assertNotNull(address.getDoor());
        assertNotNull(address.getPostalCode());
        assertNotNull(address.getTownName());

        assertEquals(4, address.getPostalCode().length());
        assertTrue(address.getPostalCode().matches("\\d{4}"),
                "Postal code should be 4 digits: " + address.getPostalCode());
    }

    @Test
    void generateAddress_numberShouldBeValid() {
        AddressDto address = fakePersonService.generateAddress();
        assertTrue(address.getNumber().matches("\\d{1,3}[A-Z]?"),
                "Number format invalid: " + address.getNumber());
    }

    @Test
    void generateAddress_floorShouldBeValid() {
        AddressDto address = fakePersonService.generateAddress();
        assertTrue(address.getFloor().equals("st") || address.getFloor().matches("\\d{1,2}"),
                "Floor format invalid: " + address.getFloor());
    }

    // --- Endpoint 7: Phone ---

    @RepeatedTest(20)
    void generatePhoneNumber_shouldBeValid() {
        String phone = fakePersonService.generatePhoneNumber();
        assertEquals(8, phone.length());
        assertTrue(phone.matches("\\d{8}"));
        boolean hasValidPrefix = PhoneService.VALID_PREFIXES.stream().anyMatch(phone::startsWith);
        assertTrue(hasValidPrefix, "Invalid phone prefix: " + phone);
    }

    // --- Endpoint 8: Full Person ---

    @Test
    void generateFullPerson_shouldReturnAllFields() {
        FakePersonDto person = fakePersonService.generateFullPerson();
        assertNotNull(person);
        assertNotNull(person.getCpr());
        assertNotNull(person.getFirstName());
        assertNotNull(person.getLastName());
        assertNotNull(person.getGender());
        assertNotNull(person.getDateOfBirth());
        assertNotNull(person.getAddress());
        assertNotNull(person.getPhoneNumber());

        assertEquals(10, person.getCpr().length());
        assertEquals(8, person.getPhoneNumber().length());
        assertEquals(4, person.getAddress().getPostalCode().length());
    }

    @RepeatedTest(10)
    void generateFullPerson_allDataShouldBeConsistent() {
        FakePersonDto person = fakePersonService.generateFullPerson();

        // CPR date matches DOB
        LocalDate dob = LocalDate.parse(person.getDateOfBirth(), DATE_FORMAT);
        String expectedDatePart = dob.format(DateTimeFormatter.ofPattern("ddMMyy"));
        assertEquals(expectedDatePart, person.getCpr().substring(0, 6));

        // CPR last digit matches gender
        int lastDigit = Character.getNumericValue(person.getCpr().charAt(9));
        if ("female".equals(person.getGender())) {
            assertEquals(0, lastDigit % 2);
        } else {
            assertEquals(1, lastDigit % 2);
        }

        // Phone has valid prefix
        boolean hasValidPrefix = PhoneService.VALID_PREFIXES.stream()
                .anyMatch(person.getPhoneNumber()::startsWith);
        assertTrue(hasValidPrefix);
    }

    // --- Endpoint 9: Bulk ---

    @Test
    void generateBulkPersons_shouldReturnCorrectCount() {
        List<FakePersonDto> persons = fakePersonService.generateBulkPersons(5);
        assertEquals(5, persons.size());
    }

    @Test
    void generateBulkPersons_minBoundary() {
        List<FakePersonDto> persons = fakePersonService.generateBulkPersons(2);
        assertEquals(2, persons.size());
    }

    @Test
    void generateBulkPersons_maxBoundary() {
        List<FakePersonDto> persons = fakePersonService.generateBulkPersons(100);
        assertEquals(100, persons.size());
    }

    @Test
    void generateBulkPersons_belowMin_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> fakePersonService.generateBulkPersons(1));
    }

    @Test
    void generateBulkPersons_aboveMax_shouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> fakePersonService.generateBulkPersons(101));
    }

    @Test
    void generateBulkPersons_allPersonsShouldBeComplete() {
        List<FakePersonDto> persons = fakePersonService.generateBulkPersons(10);
        for (FakePersonDto person : persons) {
            assertNotNull(person.getCpr());
            assertNotNull(person.getFirstName());
            assertNotNull(person.getLastName());
            assertNotNull(person.getGender());
            assertNotNull(person.getDateOfBirth());
            assertNotNull(person.getAddress());
            assertNotNull(person.getPhoneNumber());
            assertEquals(10, person.getCpr().length());
        }
    }
}
