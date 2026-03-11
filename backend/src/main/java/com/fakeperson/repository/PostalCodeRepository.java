package com.fakeperson.repository;

import com.fakeperson.model.PostalCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostalCodeRepository extends JpaRepository<PostalCode, String> {

    @Query(value = "SELECT * FROM postal_code ORDER BY RAND() LIMIT 1", nativeQuery = true)
    PostalCode findRandom();
}
