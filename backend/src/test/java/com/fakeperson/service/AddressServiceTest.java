package com.fakeperson.service;

import com.fakeperson.dto.AddressDto;
import com.fakeperson.model.PostalCode;
import com.fakeperson.repository.PostalCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private PostalCodeRepository postalCodeRepository;

    private AddressService addressService;

    @BeforeEach
    void setUp() {
        addressService = new AddressService(postalCodeRepository);
    }

    @Test
    void generateAddress_shouldReturnCompleteAddress() {
        PostalCode pc = new PostalCode();
        pc.setPostalCode("2000");
        pc.setTownName("Frederiksberg");
        when(postalCodeRepository.findRandom()).thenReturn(pc);

        AddressDto address = addressService.generateAddress();

        assertNotNull(address);
        assertNotNull(address.getStreet());
        assertNotNull(address.getNumber());
        assertNotNull(address.getFloor());
        assertNotNull(address.getDoor());
        assertEquals("2000", address.getPostalCode());
        assertEquals("Frederiksberg", address.getTownName());
    }

    @Test
    void generateStreetName_shouldEndWithVej() {
        String street = addressService.generateStreetName();
        assertTrue(street.endsWith("vej"), "Street should end with 'vej': " + street);
    }

    @Test
    void generateStreetName_shouldStartWithUppercase() {
        String street = addressService.generateStreetName();
        assertTrue(Character.isUpperCase(street.charAt(0)),
                "Street should start with uppercase: " + street);
    }

    @Test
    void generateStreetName_shouldContainOnlyAlphabeticChars() {
        String street = addressService.generateStreetName();
        assertTrue(street.matches("[A-Za-z]+"), "Street should be alphabetic: " + street);
    }

    @RepeatedTest(50)
    void generateNumber_shouldMatchPattern() {
        String number = addressService.generateNumber();
        assertTrue(number.matches("\\d{1,3}[A-Z]?"),
                "Number should be 1-999 optionally followed by uppercase letter: " + number);
    }

    @RepeatedTest(50)
    void generateFloor_shouldBeStOrNumber() {
        String floor = addressService.generateFloor();
        assertTrue(floor.equals("st") || floor.matches("\\d{1,2}"),
                "Floor should be 'st' or 1-99: " + floor);
    }

    @RepeatedTest(100)
    void generateDoor_shouldMatchValidPattern() {
        String door = addressService.generateDoor();
        boolean valid = door.equals("th") || door.equals("mf") || door.equals("tv")
                || door.matches("\\d{1,2}")
                || door.matches("[a-z]\\d")
                || door.matches("[a-z]-\\d{1,3}");
        assertTrue(valid, "Door should match valid pattern: " + door);
    }
}
