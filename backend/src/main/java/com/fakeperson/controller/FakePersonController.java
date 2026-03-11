package com.fakeperson.controller;

import com.fakeperson.dto.*;
import com.fakeperson.service.FakePersonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class FakePersonController {

    private final FakePersonService fakePersonService;

    public FakePersonController(FakePersonService fakePersonService) {
        this.fakePersonService = fakePersonService;
    }

    // 1. GET /api/cpr - Fake CPR number
    @GetMapping("/cpr")
    public ResponseEntity<Map<String, String>> getCpr() {
        return ResponseEntity.ok(Map.of("cpr", fakePersonService.generateCpr()));
    }

    // 2. GET /api/name-gender - Fake name and gender
    @GetMapping("/name-gender")
    public ResponseEntity<NameGenderDto> getNameGender() {
        return ResponseEntity.ok(fakePersonService.generateNameAndGender());
    }

    // 3. GET /api/name-gender-dob - Fake name, gender and date of birth
    @GetMapping("/name-gender-dob")
    public ResponseEntity<NameGenderDobDto> getNameGenderDob() {
        return ResponseEntity.ok(fakePersonService.generateNameGenderDob());
    }

    // 4. GET /api/cpr-name-gender - Fake CPR, name and gender
    @GetMapping("/cpr-name-gender")
    public ResponseEntity<CprNameGenderDto> getCprNameGender() {
        return ResponseEntity.ok(fakePersonService.generateCprNameGender());
    }

    // 5. GET /api/cpr-name-gender-dob - Fake CPR, name, gender and date of birth
    @GetMapping("/cpr-name-gender-dob")
    public ResponseEntity<CprNameGenderDobDto> getCprNameGenderDob() {
        return ResponseEntity.ok(fakePersonService.generateCprNameGenderDob());
    }

    // 6. GET /api/address - Fake address
    @GetMapping("/address")
    public ResponseEntity<AddressDto> getAddress() {
        return ResponseEntity.ok(fakePersonService.generateAddress());
    }

    // 7. GET /api/phone - Fake phone number
    @GetMapping("/phone")
    public ResponseEntity<Map<String, String>> getPhone() {
        return ResponseEntity.ok(Map.of("phoneNumber", fakePersonService.generatePhoneNumber()));
    }

    // 8. GET /api/person - Full fake person
    @GetMapping("/person")
    public ResponseEntity<FakePersonDto> getPerson() {
        return ResponseEntity.ok(fakePersonService.generateFullPerson());
    }

    // 9. GET /api/persons/{count} - Bulk fake persons (2-100)
    @GetMapping("/persons/{count}")
    public ResponseEntity<List<FakePersonDto>> getPersons(@PathVariable int count) {
        return ResponseEntity.ok(fakePersonService.generateBulkPersons(count));
    }
}
