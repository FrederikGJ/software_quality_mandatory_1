package IntegrationTests;
/***
 * Integration test for FakePersonService.
 *
 * These tests verify that the service layer works correctly together with
 * the underlying application components. The test cases are based on the
 * internal service methods and business rules, which makes them closer to
 * white-box testing than black-box testing.
 *
 * Tested scenarios:
 * 1. generateFullPerson() returns a complete fake person with all required fields.
 * 2. generateBulkPersons(2) returns the requested number of fake persons.
 * 3. generateBulkPersons(1) throws an IllegalArgumentException because the valid range is 2 to 100.
 ***/

import com.fakeperson.FakePersonApplication;
import com.fakeperson.dto.FakePersonDto;
import com.fakeperson.service.FakePersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = FakePersonApplication.class)
class FakePersonServiceIntegrationTest {

    @Autowired
    private FakePersonService fakePersonService;

    @Test
    void generateFullPersonShouldReturnCompleteFakePerson() {
        FakePersonDto person = fakePersonService.generateFullPerson();

        assertNotNull(person);
        assertNotNull(person.getCpr());
        assertEquals(10, person.getCpr().length());

        assertNotNull(person.getFirstName());
        assertFalse(person.getFirstName().isBlank());

        assertNotNull(person.getLastName());
        assertFalse(person.getLastName().isBlank());

        assertNotNull(person.getGender());
        assertTrue(person.getGender().equals("male") || person.getGender().equals("female"));

        assertNotNull(person.getDateOfBirth());
        assertFalse(person.getDateOfBirth().isBlank());

        assertNotNull(person.getAddress());
        assertNotNull(person.getAddress().getStreet());
        assertNotNull(person.getAddress().getNumber());
        assertNotNull(person.getAddress().getFloor());
        assertNotNull(person.getAddress().getDoor());
        assertNotNull(person.getAddress().getPostalCode());
        assertNotNull(person.getAddress().getTownName());

        assertNotNull(person.getPhoneNumber());
        assertEquals(8, person.getPhoneNumber().length());
    }

    @Test
    void generateBulkPersonsShouldReturnRequestedNumberOfPersons() {
        List<FakePersonDto> persons = fakePersonService.generateBulkPersons(2);

        assertNotNull(persons);
        assertEquals(2, persons.size());
    }

    @Test
    void generateBulkPersonsShouldThrowExceptionWhenCountIsTooLow() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                        () -> fakePersonService.generateBulkPersons(1));

        assertEquals("Count must be between 2 and 100", exception.getMessage());
    }
}