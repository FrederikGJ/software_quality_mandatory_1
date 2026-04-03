package IntegrationTests;

/*** What is being tested

 * The repository integration tests verify that the application
 * can access the database correctly through the repository layer and return valid data from the seeded tables.

 * Test cases:

 * 1. PostalCodeRepository.findRandom()
   Tests that the repository can retrieve a random postal code from the database and that the result is not null.

 * 2. PersonNameRepository.findRandom()
   Tests that the repository can retrieve a random person name from the database and that the result is not null.

 * 3. PersonNameRepository.findRandomByGender("male")
   Tests that the repository can retrieve a random male person from the database
   and that the returned gender is "male".

 * 4. PersonNameRepository.findRandomByGender("female")
   Tests that the repository can retrieve a random female person
   from the database and that the returned gender is "female". ***/



import com.fakeperson.FakePersonApplication;
import com.fakeperson.model.PersonName;
import com.fakeperson.model.PostalCode;
import com.fakeperson.repository.PersonNameRepository;
import com.fakeperson.repository.PostalCodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = FakePersonApplication.class)
class RepositoryIntegrationTest {

    @Autowired
    private PostalCodeRepository postalCodeRepository;

    @Autowired
    private PersonNameRepository personNameRepository;

    @Test
    void postalCodeRepositoryShouldReturnRandomPostalCodeFromDatabase() {
        PostalCode postalCode = postalCodeRepository.findRandom();
        assertNotNull(postalCode);
    }

    @Test
    void personNameRepositoryShouldReturnRandomPersonNameFromDatabase() {
        PersonName personName = personNameRepository.findRandom();
        assertNotNull(personName);
    }

    @Test
    void personNameRepositoryShouldReturnRandomMalePersonNameFromDatabase() {
        PersonName personName = personNameRepository.findRandomByGender("male");
        assertNotNull(personName);
        assertEquals("male", personName.getGender());
    }

    @Test
    void personNameRepositoryShouldReturnRandomFemalePersonNameFromDatabase() {
        PersonName personName = personNameRepository.findRandomByGender("female");
        assertNotNull(personName);
        assertEquals("female", personName.getGender());
    }
}