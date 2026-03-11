package com.fakeperson.repository;

import com.fakeperson.model.PersonName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonNameRepository extends JpaRepository<PersonName, Long> {

    @Query(value = "SELECT * FROM person_name ORDER BY RAND() LIMIT 1", nativeQuery = true)
    PersonName findRandom();

    @Query(value = "SELECT * FROM person_name WHERE gender = ?1 ORDER BY RAND() LIMIT 1", nativeQuery = true)
    PersonName findRandomByGender(String gender);
}
