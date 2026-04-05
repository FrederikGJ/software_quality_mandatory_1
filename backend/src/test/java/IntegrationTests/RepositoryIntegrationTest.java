package IntegrationTests;

/*** What is being tested

 * The repository integration tests verify that the application
 * can access the database correctly through the repository layer,
 * that the seeded tables contain data, and that valid data can be retrieved from the database.

 * Test cases:

 * 1. PostalCodeRepository.count()
      Tests that the postal code table contains seeded data and is not empty.

 * 2. PersonNameRepository.count()
      Tests that the person name table contains seeded data and is not empty.

 * 3. PostalCodeRepository.findRandom()
      Tests that the repository can retrieve a random postal code from the database
      and that the result is not null.

 * 4. PersonNameRepository.findRandom()
      Tests that the repository can retrieve a random person name from the database
      and that the result is not null.

 * 5. PersonNameRepository.findRandomByGender("male")
      Tests that the repository can retrieve a random male person from the database
      and that the returned gender is "male".

 * 6. PersonNameRepository.findRandomByGender("female")
      Tests that the repository can retrieve a random female person from the database
      and that the returned gender is "female".
 ***/

import com.fakeperson.FakePersonApplication;
import com.fakeperson.model.PersonName;
import com.fakeperson.model.PostalCode;
import com.fakeperson.repository.PersonNameRepository;
import com.fakeperson.repository.PostalCodeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FakePersonApplication.class)
class RepositoryIntegrationTest {

    @Autowired
    private PostalCodeRepository postalCodeRepository;

    @Autowired
    private PersonNameRepository personNameRepository;

    @Test
    void postalCodeRepositoryShouldContainSeededData() {
        assertTrue(postalCodeRepository.count() > 0);
    }

    @Test
    void personNameRepositoryShouldContainSeededData() {
        assertTrue(personNameRepository.count() > 0);
    }

    @Test
    void postalCodeRepositoryShouldReturnRandomPostalCodeFromDatabase() {
        PostalCode postalCode = postalCodeRepository.findRandom();

        assertNotNull(postalCode);
        assertNotNull(postalCode.getPostalCode());
        assertNotNull(postalCode.getTownName());
        assertFalse(postalCode.getPostalCode().isBlank());
        assertFalse(postalCode.getTownName().isBlank());
        assertTrue(postalCode.getPostalCode().matches("\\d{4}"));
    }

    @Test
    void personNameRepositoryShouldReturnRandomPersonNameFromDatabase() {
        PersonName personName = personNameRepository.findRandom();

        assertNotNull(personName);
        assertNotNull(personName.getFirstName());
        assertNotNull(personName.getLastName());
        assertNotNull(personName.getGender());
        assertFalse(personName.getFirstName().isBlank());
        assertFalse(personName.getLastName().isBlank());
        assertTrue(personName.getGender().equals("male") || personName.getGender().equals("female"));
    }

    @Test
    void personNameRepositoryShouldReturnRandomMalePersonNameFromDatabase() {
        PersonName personName = personNameRepository.findRandomByGender("male");

        assertNotNull(personName);
        assertEquals("male", personName.getGender());
        assertNotNull(personName.getFirstName());
        assertNotNull(personName.getLastName());
        assertFalse(personName.getFirstName().isBlank());
        assertFalse(personName.getLastName().isBlank());
    }

    @Test
    void personNameRepositoryShouldReturnRandomFemalePersonNameFromDatabase() {
        PersonName personName = personNameRepository.findRandomByGender("female");

        assertNotNull(personName);
        assertEquals("female", personName.getGender());
        assertNotNull(personName.getFirstName());
        assertNotNull(personName.getLastName());
        assertFalse(personName.getFirstName().isBlank());
        assertFalse(personName.getLastName().isBlank());
    }
}