package com.safetynet.alerts.web.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

    @Mock
    private PersonService personService;

    @InjectMocks
    private PersonController personController;
    Person oldPerson, newPerson;

    @BeforeEach
    public void setUp() {
        oldPerson = new Person(0, "Quentin", "Beraud", "Rue du 123", "Lyon", "69000", "06 78 79 80 81",
                "qbe.pro@yahoo.com");
        newPerson = new Person(0, "Quentin", "Beraud", "Rue du 123" + "1", "Lyon" + "1", "69000" + "1",
                "06 78 79 80 81" + "1",
                "qbe.pro@yahoo.com" + "1");
    }


    /**
     * SomeJavadoc.
     * Test case for finding a person by ID.
     */
    @Test
    void testUpdateByFirstNameAndLastName() {
        when(personService.findPersonByFirstNameAndLastName(oldPerson.getFirstName(), oldPerson.getLastName())).thenReturn(oldPerson);
        personController.updateByFirstNameAndLastName(oldPerson.getFirstName(), oldPerson.getLastName(), newPerson);
        verify(personService, (times(1))).savePerson(newPerson);
    }

    /**
     * SomeJavadoc.
     * Test case for adding a person.
     */
    @Test
    void testAddPerson() {
        personController.addPerson(oldPerson);
        verify(personService, times(1)).savePerson(oldPerson);
    }

    /**
     * SomeJavadoc.
     * Test case for deleting a person by first name and last name.
     */
    @Test
    void testDeleteByFirstNameAndLastName() {
        personController.deleteByFirstNameAndLastName(oldPerson.getFirstName(), oldPerson.getLastName());
        verify(personService, times(1)).deletePersonByFirstNameAndLastName(oldPerson.getFirstName(),
                oldPerson.getLastName());
    }
}
