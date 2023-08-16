package com.safetynet.alerts.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.web.dao.PersonDao;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {
    @Mock
    private PersonDao personDao;

    @InjectMocks
    private PersonService personService;

    List<String> addresses;
    String firstName, lastName, city, address;
    List<Person> persons, adults, children, redidents;
    Person person_1, person_2;
    List<MedicalRecord> medicalRecords;
    MedicalRecord medicalRecord_1, medicalRecord_2;

    @BeforeEach
    public void setUp() {
        firstName = "Quentin";
        lastName = "Beraud";
        city = "Lyon";
        address = "ici";
        person_1 = new Person(1, firstName, lastName, "ici", "Lyon", "69000", "06 66 67 72 23", "qbe.pro@yahoo");
        person_2 = new Person(2, "Quentin_2", "Beraud_2", "pas ici", "Lyon", "69000", "06 66 67 72 23",
                "qbe.pro@yahoo");
        persons = new ArrayList<>();
        persons.add(person_1);
        persons.add(person_2);

        addresses = Arrays.asList("ici", "ici ici");

        medicalRecords = new ArrayList<>();
        medicalRecord_1 = new MedicalRecord(1, firstName, lastName, "12/31/1997", null, null);
        medicalRecord_2 = new MedicalRecord(1, "Quentin_2", "Beraud_2", "12/31/2020", null, null);
        medicalRecords.add(medicalRecord_1);
        medicalRecords.add(medicalRecord_2);

        adults = new ArrayList<>();
        adults.add(person_1);

        children = new ArrayList<>();
        children.add(person_2);

        redidents = new ArrayList<>();
        redidents.add(person_1);
    }

    /**
     * SomeJavadoc.
     * Test for 'deletePersonById' method.
     */
    @Test
    void testDeletePersonById() {
        int id = 1;
        personService.deletePersonById(id);
        verify(personDao, times(1)).deleteById(id);
    }

    /**
     * SomeJavadoc.
     * Test for 'findPersonByFirstNameAndLastName' method.
     */
    @Test
    void testFindPersonByFirstNameAndLastName() {
        personService.findPersonByFirstNameAndLastName(firstName, lastName);
        verify(personDao, times(1)).findByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * SomeJavadoc.
     * Test for 'getAll' method.
     */
    @Test
    void testGetAll() {
        personService.getAll();
        verify(personDao, times(1)).findAll();
    }

    /**
     * SomeJavadoc.
     * Test for 'getAdults' method.
     */
    @Test
    void testGetAdults() {
        assertEquals(adults, personService.getAdults(persons, medicalRecords));
    }

    /**
     * SomeJavadoc.
     * Test for 'getChildren' method.
     */
    @Test
    void testGetChildren() {
        assertEquals(children, personService.getChildren(persons, medicalRecords));
    }

    /**
     * SomeJavadoc.
     * Test for 'getPersonById' method.
     */
    @Test
    void testGetPersonById() {
        int id = 1;
        personService.getPersonById(id);
        verify(personDao, times(1)).findById(id);
    }

    /**
     * SomeJavadoc.
     * Test for 'getPersonsByAddresses' method.
     */
    @Test
    void testGetPersonsByAddresses() {
        when(personService.getAll()).thenReturn(persons);
        assertEquals(adults, personService.getPersonsByAddresses(addresses));
    }

    /**
     * SomeJavadoc.
     * Test for 'getPersonsByCity' method.
     */
    @Test
    void testGetPersonsByCity() {
        when(personService.getAll()).thenReturn(persons);
        assertEquals(persons, personService.getPersonsByCity(city));
    }

    /**
     * SomeJavadoc.
     * Test for 'getPersonsByFirstNameAndLastName' method.
     */
    @Test
    void testGetPersonsByFirstNameAndLastName() {
        when(personService.getAll()).thenReturn(persons);
        assertEquals(redidents, personService.getPersonsByFirstNameAndLastName(firstName, lastName));
    }

    /**
     * SomeJavadoc.
     * Test for 'getPersonsListByAddress' method.
     */
    @Test
    void testGetPersonsListByAddress() {
        when(personService.getAll()).thenReturn(persons);
        assertEquals(redidents, personService.getPersonsListByAddress(address));
    }

    /**
     * SomeJavadoc.
     * Test for 'savePerson' method.
     */
    @Test
    void testSavePerson() {
        personService.savePerson(person_1);
        verify(personDao, times(1)).save(person_1);
    }
}
