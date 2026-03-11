package com.fakeperson.service;

import com.fakeperson.dto.AddressDto;
import com.fakeperson.model.PostalCode;
import com.fakeperson.repository.PostalCodeRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class AddressService {

    private final PostalCodeRepository postalCodeRepository;

    public AddressService(PostalCodeRepository postalCodeRepository) {
        this.postalCodeRepository = postalCodeRepository;
    }

    public AddressDto generateAddress() {
        String street = generateStreetName();
        String number = generateNumber();
        String floor = generateFloor();
        String door = generateDoor();
        PostalCode pc = postalCodeRepository.findRandom();
        return new AddressDto(street, number, floor, door, pc.getPostalCode(), pc.getTownName());
    }

    String generateStreetName() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int length = random.nextInt(5, 15);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) ('a' + random.nextInt(26));
            if (i == 0) c = Character.toUpperCase(c);
            sb.append(c);
        }
        sb.append("vej");
        return sb.toString();
    }

    String generateNumber() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int num = random.nextInt(1, 1000);
        if (random.nextBoolean()) {
            char letter = (char) ('A' + random.nextInt(26));
            return num + "" + letter;
        }
        return String.valueOf(num);
    }

    String generateFloor() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (random.nextInt(10) == 0) {
            return "st";
        }
        return String.valueOf(random.nextInt(1, 100));
    }

    String generateDoor() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int type = random.nextInt(4);
        switch (type) {
            case 0: return new String[]{"th", "mf", "tv"}[random.nextInt(3)];
            case 1: return String.valueOf(random.nextInt(1, 51));
            case 2: {
                char c = (char) ('a' + random.nextInt(26));
                if (random.nextBoolean()) {
                    return c + "-" + random.nextInt(1, 1000);
                }
                return c + String.valueOf(random.nextInt(1, 10));
            }
            default: return "th";
        }
    }
}
