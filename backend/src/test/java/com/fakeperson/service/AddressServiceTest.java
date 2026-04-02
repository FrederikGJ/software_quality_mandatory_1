package com.fakeperson.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressServiceTest {

    private AddressService addressService;

    @BeforeEach
    void setUp() {
        addressService = new AddressService(null);
    }

    // =============================================
    // generateStreetName() - EP & BVA
    // =============================================

    @RepeatedTest(100)
    void generateStreetName_shouldHaveLengthBetween8And17() {
        String street = addressService.generateStreetName();
        // Min length: 5 random chars + "vej" = 8
        // Max length: 14 random chars + "vej" = 17
        assertTrue(street.length() >= 8, "Street name too short: " + street);
        assertTrue(street.length() <= 17, "Street name too long: " + street);
    }

    @RepeatedTest(100)
    void generateStreetName_shouldStartWithUpperCase() {
        String street = addressService.generateStreetName();
        assertTrue(Character.isUpperCase(street.charAt(0)),
                "Street name should start with uppercase: " + street);
    }

    @RepeatedTest(100)
    void generateStreetName_shouldEndWithVej() {
        String street = addressService.generateStreetName();
        assertTrue(street.endsWith("vej"),
                "Street name should end with 'vej': " + street);
    }

    @RepeatedTest(100)
    void generateStreetName_shouldOnlyContainLetters() {
        String street = addressService.generateStreetName();
        assertTrue(street.matches("[A-Za-z]+"),
                "Street name should only contain letters: " + street);
    }

    @RepeatedTest(100)
    void generateStreetName_middleCharsShouldBeLowerCase() {
        String street = addressService.generateStreetName();
        // Characters after the first and before "vej" should be lowercase
        String middle = street.substring(1);
        assertEquals(middle, middle.toLowerCase(),
                "All chars after first should be lowercase: " + street);
    }

    // =============================================
    // generateNumber() - EP & BVA
    // =============================================

    @RepeatedTest(100)
    void generateNumber_shouldNotBeEmpty() {
        String number = addressService.generateNumber();
        assertFalse(number.isEmpty(), "Number should not be empty");
    }

    @RepeatedTest(100)
    void generateNumber_shouldHaveMaxLength4() {
        String number = addressService.generateNumber();
        assertTrue(number.length() >= 1, "Number too short: " + number);
        assertTrue(number.length() <= 4, "Number too long: " + number);
    }

    @RepeatedTest(100)
    void generateNumber_numericPartShouldBeBetween1And999() {
        String number = addressService.generateNumber();
        // Extract numeric part (strip trailing letter if present)
        String numericPart = number.replaceAll("[A-Z]$", "");
        int num = Integer.parseInt(numericPart);
        assertTrue(num >= 1, "Number part too small: " + number);
        assertTrue(num <= 999, "Number part too large: " + number);
    }

    @RepeatedTest(100)
    void generateNumber_ifLetterPresent_shouldBeUpperCaseAtoZ() {
        String number = addressService.generateNumber();
        if (number.matches(".*[A-Z]$")) {
            char letter = number.charAt(number.length() - 1);
            assertTrue(letter >= 'A' && letter <= 'Z',
                    "Letter should be A-Z: " + number);
            // Rest should be digits
            String numPart = number.substring(0, number.length() - 1);
            assertTrue(numPart.matches("\\d+"),
                    "Part before letter should be digits: " + number);
        }
    }

    // =============================================
    // generateFloor() - EP & BVA
    // =============================================

    @RepeatedTest(100)
    void generateFloor_shouldBeStOrNumberFrom1To99() {
        String floor = addressService.generateFloor();
        if (floor.equals("st")) {
            // Valid - stuen partition
            return;
        }
        int num = Integer.parseInt(floor);
        assertTrue(num >= 1, "Floor too low: " + floor);
        assertTrue(num <= 99, "Floor too high: " + floor);
    }

    @RepeatedTest(100)
    void generateFloor_shouldHaveLength1Or2() {
        String floor = addressService.generateFloor();
        assertTrue(floor.length() >= 1, "Floor too short: " + floor);
        assertTrue(floor.length() <= 2, "Floor too long: " + floor);
    }

    @RepeatedTest(100)
    void generateFloor_shouldNeverBeZeroOrNegative() {
        String floor = addressService.generateFloor();
        if (!floor.equals("st")) {
            int num = Integer.parseInt(floor);
            assertTrue(num > 0, "Floor should not be 0 or negative: " + floor);
        }
    }

    // =============================================
    // generateDoor() - EP & BVA per case
    // =============================================

    @RepeatedTest(500)
    void generateDoor_shouldMatchOneOfTheValidPatterns() {
        String door = addressService.generateDoor();
        // Case 0: "th", "mf", "tv"
        // Case 1: number 1-50
        // Case 2: letter + "-" + number(1-999) OR letter + number(1-9)
        // Case 3: "th"
        boolean isThMfTv = door.equals("th") || door.equals("mf") || door.equals("tv");
        boolean isNumber = door.matches("\\d+");
        boolean isLetterDashNumber = door.matches("[a-z]-\\d{1,3}");
        boolean isLetterNumber = door.matches("[a-z]\\d");

        assertTrue(isThMfTv || isNumber || isLetterDashNumber || isLetterNumber,
                "Door doesn't match any valid pattern: " + door);
    }

    @RepeatedTest(500)
    void generateDoor_shouldNotBeEmpty() {
        String door = addressService.generateDoor();
        assertFalse(door.isEmpty(), "Door should not be empty");
    }

    @RepeatedTest(500)
    void generateDoor_ifNumeric_shouldBeBetween1And50() {
        String door = addressService.generateDoor();
        if (door.matches("\\d+")) {
            int num = Integer.parseInt(door);
            assertTrue(num >= 1, "Door number too small: " + door);
            assertTrue(num <= 50, "Door number too large: " + door);
        }
    }

    @RepeatedTest(500)
    void generateDoor_case2_letterDashNumber_numberShouldBe1To999() {
        String door = addressService.generateDoor();
        if (door.matches("[a-z]-\\d+")) {
            int num = Integer.parseInt(door.substring(2));
            assertTrue(num >= 1, "Case 2 number too small: " + door);
            assertTrue(num <= 999, "Case 2 number too large: " + door);
        }
    }

    @RepeatedTest(500)
    void generateDoor_case2_letterNumber_numberShouldBe1To9() {
        String door = addressService.generateDoor();
        if (door.matches("[a-z]\\d")) {
            int num = Character.getNumericValue(door.charAt(1));
            assertTrue(num >= 1, "Case 2 short number too small: " + door);
            assertTrue(num <= 9, "Case 2 short number too large: " + door);
        }
    }

    @RepeatedTest(500)
    void generateDoor_shouldNotExceed5Characters() {
        String door = addressService.generateDoor();
        assertTrue(door.length() <= 5,
                "Door string too long (max 5 chars): " + door);
    }
}
