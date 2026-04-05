package com.fakeperson.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import static org.junit.jupiter.api.Assertions.*;

class PhoneServiceTest {

    private PhoneService phoneService;

    @BeforeEach
    void setUp() {
        phoneService = new PhoneService();
    }

    // =============================================
    // generatePhoneNumber() - EP & BVA
    // =============================================

    @RepeatedTest(100)
    void generatePhoneNumber_shouldAlwaysBe8Digits() {
        String phone = phoneService.generatePhoneNumber();
        assertEquals(8, phone.length(),
                "Phone number should be exactly 8 digits: " + phone);
    }

    @RepeatedTest(100)
    void generatePhoneNumber_shouldOnlyContainDigits() {
        String phone = phoneService.generatePhoneNumber();
        assertTrue(phone.matches("\\d{8}"),
                "Phone number should only contain digits: " + phone);
    }

    @RepeatedTest(100)
    void generatePhoneNumber_shouldStartWithValidPrefix() {
        String phone = phoneService.generatePhoneNumber();
        boolean hasValidPrefix = PhoneService.VALID_PREFIXES.stream()
                .anyMatch(phone::startsWith);
        assertTrue(hasValidPrefix,
                "Phone number should start with a valid prefix: " + phone);
    }

    @RepeatedTest(100)
    void generatePhoneNumber_shouldNotBeShorterThan8() {
        String phone = phoneService.generatePhoneNumber();
        assertTrue(phone.length() >= 8,
                "Phone number too short: " + phone);
    }

    @RepeatedTest(100)
    void generatePhoneNumber_shouldNotBeLongerThan8() {
        String phone = phoneService.generatePhoneNumber();
        assertTrue(phone.length() <= 8,
                "Phone number too long: " + phone);
    }

    @RepeatedTest(100)
    void generatePhoneNumber_shouldNotContainLetters() {
        String phone = phoneService.generatePhoneNumber();
        assertFalse(phone.matches(".*[a-zA-Z].*"),
                "Phone number should not contain letters: " + phone);
    }
}
