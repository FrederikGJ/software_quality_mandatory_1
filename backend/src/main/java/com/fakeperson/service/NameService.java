package com.fakeperson.service;

import com.fakeperson.dto.NameGenderDto;
import com.fakeperson.model.PersonName;
import com.fakeperson.repository.PersonNameRepository;
import org.springframework.stereotype.Service;

@Service
public class NameService {

    private final PersonNameRepository personNameRepository;

    public NameService(PersonNameRepository personNameRepository) {
        this.personNameRepository = personNameRepository;
    }

    public NameGenderDto generateRandomName() {
        PersonName person = personNameRepository.findRandom();
        return new NameGenderDto(person.getFirstName(), person.getLastName(), person.getGender());
    }

    public NameGenderDto generateRandomNameByGender(String gender) {
        PersonName person = personNameRepository.findRandomByGender(gender);
        return new NameGenderDto(person.getFirstName(), person.getLastName(), person.getGender());
    }
}
