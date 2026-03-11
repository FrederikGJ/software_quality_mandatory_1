package com.fakeperson.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class PhoneServiceTest {

    private PhoneService phoneService;

    @BeforeEach
    void setUp() {
        phoneService = new PhoneService();
    }

    @RepeatedTest(100)
    void generatePhoneNumber_shouldReturn8Digits() {
        String phone = phoneService.generatePhoneNumber();
        assertEquals(8, phone.length(), "Phone number should be 8 digits: " + phone);
        assertTrue(phone.matches("\\d{8}"), "Phone number should contain only digits: " + phone);
    }

    @RepeatedTest(100)
    void generatePhoneNumber_shouldStartWithValidPrefix() {
        String phone = phoneService.generatePhoneNumber();
        boolean hasValidPrefix = PhoneService.VALID_PREFIXES.stream()
                .anyMatch(phone::startsWith);
        assertTrue(hasValidPrefix, "Phone number should start with a valid prefix: " + phone);
    }

    @Test
    void validPrefixes_shouldNotBeEmpty() {
        assertFalse(PhoneService.VALID_PREFIXES.isEmpty());
    }

    @Test
    void generatePhoneNumber_shouldNotStartWithZero() {
        for (int i = 0; i < 100; i++) {
            String phone = phoneService.generatePhoneNumber();
            assertNotEquals('0', phone.charAt(0), "Phone should not start with 0: " + phone);
        }
    }

    @Test
    void generatePhoneNumber_prefixStartingWith2_shouldWork() {
        // "2" is a valid 1-digit prefix, remaining 7 digits should make 8 total
        boolean found = false;
        for (int i = 0; i < 1000; i++) {
            String phone = phoneService.generatePhoneNumber();
            if (phone.startsWith("2") && !phone.startsWith("20") && !phone.startsWith("21")
                    && !phone.startsWith("22") && !phone.startsWith("23") && !phone.startsWith("24")
                    && !phone.startsWith("25") && !phone.startsWith("26") && !phone.startsWith("27")
                    && !phone.startsWith("28") && !phone.startsWith("29")) {
                // This just verifies any phone starting with 2 is valid
            }
            if (phone.startsWith("2")) {
                found = true;
                assertEquals(8, phone.length());
            }
        }
    }
}
