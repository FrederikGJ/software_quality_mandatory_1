package com.fakeperson.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class CprService {

    private static final DateTimeFormatter CPR_DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMyy");

    public LocalDate generateRandomBirthDate() {
        long minDay = LocalDate.of(1900, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    public String generateCpr(LocalDate birthDate, String gender) {
        String datePart = birthDate.format(CPR_DATE_FORMAT);
        String lastFour = generateLastFourDigits(gender);
        return datePart + lastFour;
    }

    String generateLastFourDigits(String gender) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int first3 = random.nextInt(0, 1000);
        int lastDigit;
        if ("female".equalsIgnoreCase(gender)) {
            lastDigit = random.nextInt(0, 5) * 2; // 0, 2, 4, 6, 8
        } else {
            lastDigit = random.nextInt(0, 5) * 2 + 1; // 1, 3, 5, 7, 9
        }
        return String.format("%03d%d", first3, lastDigit);
    }
}
