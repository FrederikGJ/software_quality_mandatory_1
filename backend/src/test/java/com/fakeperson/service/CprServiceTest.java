package com.fakeperson.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CprServiceTest {

    private CprService cprService;

    @BeforeEach
    void setUp() {
        cprService = new CprService();
    }

    // =============================================
    // generateRandomBirthDate() - EP & BVA
    // =============================================

    @RepeatedTest(100)
    void generateRandomBirthDate_shouldNotBeBeforeYear1900() {
        LocalDate date = cprService.generateRandomBirthDate();
        assertFalse(date.isBefore(LocalDate.of(1900, 1, 1)),
                "Date should not be before 1900-01-01: " + date);
    }

    @RepeatedTest(100)
    void generateRandomBirthDate_shouldNotBeInTheFuture() {
        LocalDate date = cprService.generateRandomBirthDate();
        assertFalse(date.isAfter(LocalDate.now()),
                "Date should not be in the future: " + date);
    }

    @RepeatedTest(100)
    void generateRandomBirthDate_shouldBeWithinValidRange() {
        LocalDate date = cprService.generateRandomBirthDate();
        LocalDate minDate = LocalDate.of(1900, 1, 1);
        LocalDate maxDate = LocalDate.now();
        assertTrue(!date.isBefore(minDate) && !date.isAfter(maxDate),
                "Date should be between 1900-01-01 and today: " + date);
    }

    // BVA: boundary dates
    @Test
    void generateRandomBirthDate_boundaryMinDateShouldBeValid() {
        // 1900-01-01 is the minimum valid date - just verify it's reachable
        // We can't force it, but we can verify the range logic
        LocalDate minDate = LocalDate.of(1900, 1, 1);
        for (int i = 0; i < 1000; i++) {
            LocalDate date = cprService.generateRandomBirthDate();
            assertFalse(date.isBefore(minDate));
        }
    }

    // =============================================
    // generateCpr(LocalDate, String) - EP & BVA
    // =============================================

    @Test
    void generateCpr_withValidDateAndMale_shouldReturn10Digits() {
        LocalDate date = LocalDate.of(1990, 5, 15);
        String cpr = cprService.generateCpr(date, "male");
        assertEquals(10, cpr.length(), "CPR should be 10 characters: " + cpr);
        assertTrue(cpr.matches("\\d{10}"), "CPR should be all digits: " + cpr);
    }

    @Test
    void generateCpr_withValidDateAndFemale_shouldReturn10Digits() {
        LocalDate date = LocalDate.of(1985, 12, 1);
        String cpr = cprService.generateCpr(date, "female");
        assertEquals(10, cpr.length(), "CPR should be 10 characters: " + cpr);
        assertTrue(cpr.matches("\\d{10}"), "CPR should be all digits: " + cpr);
    }

    @Test
    void generateCpr_shouldStartWithFormattedDate_ddMMyy() {
        LocalDate date = LocalDate.of(1990, 5, 15);
        String cpr = cprService.generateCpr(date, "male");
        assertEquals("150590", cpr.substring(0, 6),
                "CPR should start with ddMMyy: " + cpr);
    }

    @Test
    void generateCpr_withLeapYearDate_shouldWork() {
        LocalDate date = LocalDate.of(2000, 2, 29);
        String cpr = cprService.generateCpr(date, "female");
        assertEquals("290200", cpr.substring(0, 6));
        assertEquals(10, cpr.length());
    }

    @Test
    void generateCpr_withMinBoundaryDate_shouldWork() {
        LocalDate date = LocalDate.of(1900, 1, 1);
        String cpr = cprService.generateCpr(date, "male");
        assertEquals("010100", cpr.substring(0, 6));
        assertEquals(10, cpr.length());
    }

    // Negative test: null gender should default to male (odd last digit)
    @Test
    void generateCpr_withNullGender_shouldProduceOddLastDigit() {
        LocalDate date = LocalDate.of(1990, 5, 15);
        String cpr = cprService.generateCpr(date, null);
        int lastDigit = Character.getNumericValue(cpr.charAt(9));
        assertTrue(lastDigit % 2 == 1,
                "Null gender should give odd last digit (like male): " + cpr);
    }

    // =============================================
    // generateLastFourDigits(String gender) - EP & BVA
    // =============================================

    @RepeatedTest(100)
    void generateLastFourDigits_female_lastDigitShouldBeEven() {
        String digits = cprService.generateLastFourDigits("female");
        int lastDigit = Character.getNumericValue(digits.charAt(3));
        assertTrue(lastDigit % 2 == 0,
                "Female last digit should be even: " + digits);
        assertTrue(Set.of(0, 2, 4, 6, 8).contains(lastDigit),
                "Female last digit should be in {0,2,4,6,8}: " + digits);
    }

    @RepeatedTest(100)
    void generateLastFourDigits_male_lastDigitShouldBeOdd() {
        String digits = cprService.generateLastFourDigits("male");
        int lastDigit = Character.getNumericValue(digits.charAt(3));
        assertTrue(lastDigit % 2 == 1,
                "Male last digit should be odd: " + digits);
        assertTrue(Set.of(1, 3, 5, 7, 9).contains(lastDigit),
                "Male last digit should be in {1,3,5,7,9}: " + digits);
    }

    @RepeatedTest(100)
    void generateLastFourDigits_shouldAlwaysBe4Characters() {
        String digitsM = cprService.generateLastFourDigits("male");
        String digitsF = cprService.generateLastFourDigits("female");
        assertEquals(4, digitsM.length(), "Should be 4 chars: " + digitsM);
        assertEquals(4, digitsF.length(), "Should be 4 chars: " + digitsF);
    }

    @RepeatedTest(100)
    void generateLastFourDigits_first3DigitsShouldBe000To999() {
        String digits = cprService.generateLastFourDigits("male");
        int first3 = Integer.parseInt(digits.substring(0, 3));
        assertTrue(first3 >= 0, "First 3 digits too small: " + digits);
        assertTrue(first3 <= 999, "First 3 digits too large: " + digits);
    }

    // Case insensitive test
    @RepeatedTest(50)
    void generateLastFourDigits_femaleCaseInsensitive_shouldBeEven() {
        for (String g : new String[]{"Female", "FEMALE", "fEmAlE"}) {
            String digits = cprService.generateLastFourDigits(g);
            int lastDigit = Character.getNumericValue(digits.charAt(3));
            assertTrue(lastDigit % 2 == 0,
                    "Case-insensitive female should give even: " + g + " -> " + digits);
        }
    }

    // Negative test: null gender should behave like male (odd)
    @RepeatedTest(50)
    void generateLastFourDigits_nullGender_shouldGiveOddLastDigit() {
        String digits = cprService.generateLastFourDigits(null);
        int lastDigit = Character.getNumericValue(digits.charAt(3));
        assertTrue(lastDigit % 2 == 1,
                "Null gender should give odd last digit: " + digits);
    }

    // Negative test: unknown gender string should behave like male (odd)
    @RepeatedTest(50)
    void generateLastFourDigits_unknownGender_shouldGiveOddLastDigit() {
        String digits = cprService.generateLastFourDigits("unknown");
        int lastDigit = Character.getNumericValue(digits.charAt(3));
        assertTrue(lastDigit % 2 == 1,
                "Unknown gender should give odd last digit: " + digits);
    }

    // Negative test: empty string gender should behave like male (odd)
    @RepeatedTest(50)
    void generateLastFourDigits_emptyGender_shouldGiveOddLastDigit() {
        String digits = cprService.generateLastFourDigits("");
        int lastDigit = Character.getNumericValue(digits.charAt(3));
        assertTrue(lastDigit % 2 == 1,
                "Empty gender should give odd last digit: " + digits);
    }
}
