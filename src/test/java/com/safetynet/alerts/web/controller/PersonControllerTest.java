package com.safetynet.alerts.web.controller;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.safetynet.alerts.web.deserialization.model.PersonDeserialization;
import com.safetynet.alerts.web.service.PersonService;

@ExtendWith(MockitoExtension.class)
public class PersonControllerTest {

  @Mock
  private PersonService personService;

  @InjectMocks
  private PersonController personController;
  PersonDeserialization person;
  String FIRSTNAME, LASTNAME;

  @BeforeEach
  public void setUp() {
    FIRSTNAME = "Quentin";
    LASTNAME = "Beraud";
    person = new PersonDeserialization(0, FIRSTNAME, LASTNAME, "Rue du 123", "Lyon", "69000", "06 78 79 80 81",
        "qbe.pro@yahoo.com");
  }

  @Test
  void testAddPerson() {
    personController.addPerson(person);
    verify(personService, times(1)).addPerson(person, "addPerson");
  }

  @Test
  void testAddPersonWithNullProperty() {
    person.setCity(null);
    ResponseEntity<String> result = personController.addPerson(person);
    ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect content.");
    assertEquals(expected, result);
    verify(personService, times(0)).addPerson(person, "addPerson");
  }

  @Test
  void testDeleteByFirstNameAndLastName() {
    personController.deleteByFirstAndLastName(FIRSTNAME, LASTNAME);
    verify(personService, times(1)).deleteByFirstAndLastName(FIRSTNAME, LASTNAME, "deleteByFirstAndLastName");
  }

  @Test
  void testGetAllPersons() {
    personController.getAllPersons();
    verify(personService, times(1)).getAllPersons();
  }

  @Test
  void testUpdatePersonByFirstAndLastName() {
    personController.updatePersonByFirstAndLastName(FIRSTNAME, LASTNAME, person);
    verify(personService, times(1)).updateByFirstAndLastName(FIRSTNAME, LASTNAME, person,
        "updatePersonByFirstAndLastName");
  }

  @Test
  void testUpdatePersonByFirstAndLastNameWithNullProperty() {
    person.setCity(null);
    ResponseEntity<String> result = personController.updatePersonByFirstAndLastName(FIRSTNAME, LASTNAME, person);
    ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect content.");
    assertEquals(expected, result);
    verify(personService, times(0)).updateByFirstAndLastName(FIRSTNAME, LASTNAME, person,
        "updatePersonByFirstAndLastName");
  }
}