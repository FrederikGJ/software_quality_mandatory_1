package com.fakeperson.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CprServiceTest {

    private CprService cprService;

    @BeforeEach
    void setUp() {
        cprService = new CprService();
    }

    @Test
    void generateRandomBirthDate_shouldReturnDateInPast() {
        LocalDate date = cprService.generateRandomBirthDate();
        assertNotNull(date);
        assertTrue(date.isBefore(LocalDate.now()));
        assertTrue(date.isAfter(LocalDate.of(1899, 12, 31)));
    }

    @Test
    void generateCpr_shouldReturn10Digits() {
        LocalDate date = LocalDate.of(1990, 3, 15);
        String cpr = cprService.generateCpr(date, "male");
        assertEquals(10, cpr.length());
        assertTrue(cpr.matches("\\d{10}"));
    }

    @Test
    void generateCpr_shouldStartWithCorrectDate() {
        LocalDate date = LocalDate.of(1990, 3, 15);
        String cpr = cprService.generateCpr(date, "male");
        assertEquals("150390", cpr.substring(0, 6));
    }

    @RepeatedTest(50)
    void generateCpr_malesShouldHaveOddLastDigit() {
        LocalDate date = LocalDate.of(1990, 1, 1);
        String cpr = cprService.generateCpr(date, "male");
        int lastDigit = Character.getNumericValue(cpr.charAt(9));
        assertEquals(1, lastDigit % 2, "Last digit should be odd for males: " + cpr);
    }

    @RepeatedTest(50)
    void generateCpr_femalesShouldHaveEvenLastDigit() {
        LocalDate date = LocalDate.of(1990, 1, 1);
        String cpr = cprService.generateCpr(date, "female");
        int lastDigit = Character.getNumericValue(cpr.charAt(9));
        assertEquals(0, lastDigit % 2, "Last digit should be even for females: " + cpr);
    }

    @Test
    void generateCpr_datePartMatchesBirthDate() {
        LocalDate date = LocalDate.of(2000, 12, 25);
        String cpr = cprService.generateCpr(date, "female");
        assertEquals("251200", cpr.substring(0, 6));
    }

    @Test
    void generateLastFourDigits_shouldReturn4Digits() {
        String digits = cprService.generateLastFourDigits("male");
        assertEquals(4, digits.length());
        assertTrue(digits.matches("\\d{4}"));
    }

    @Test
    void generateCpr_leapYearDate() {
        LocalDate date = LocalDate.of(2000, 2, 29);
        String cpr = cprService.generateCpr(date, "male");
        assertEquals("290200", cpr.substring(0, 6));
    }

    @Test
    void generateCpr_firstDayOfYear() {
        LocalDate date = LocalDate.of(1985, 1, 1);
        String cpr = cprService.generateCpr(date, "female");
        assertEquals("010185", cpr.substring(0, 6));
    }

    @Test
    void generateCpr_lastDayOfYear() {
        LocalDate date = LocalDate.of(1999, 12, 31);
        String cpr = cprService.generateCpr(date, "male");
        assertEquals("311299", cpr.substring(0, 6));
    }
}
