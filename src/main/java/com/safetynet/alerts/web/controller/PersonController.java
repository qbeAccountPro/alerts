package com.safetynet.alerts.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.safetynet.alerts.web.communUtilts.DataManipulationUtils;
import com.safetynet.alerts.web.deserialization.model.PersonDeserialization;
import com.safetynet.alerts.web.logging.EndpointsLogger;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.service.PersonService;

/**
 * Some javadoc.
 * 
 * This class represents a REST controller for managing persons.
 * It provides endpoints for retrieving all persons, finding a person by ID,
 * adding new persons, updating existing persons, and deleting persons.
 */
@RestController
@RequestMapping("/person")
public class PersonController {

  @Autowired
  private final PersonService personService;
  private final DataManipulationUtils beanService;
  private EndpointsLogger log = new EndpointsLogger();

  public PersonController(PersonService personService) {
    this.personService = personService;
    this.beanService = new DataManipulationUtils();
  }

  /**
   * Some javadoc.
   * 
   * Adds a new person to the system.
   *
   * @param person The Person object representing the new person to be added.
   */
  @PostMapping(value = "")
  public ResponseEntity<String> addPerson(@RequestBody PersonDeserialization personDeserialize) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);

    // Check the request content :
    Boolean fieldsAreNull = beanService.areFieldsNullExceptId(personDeserialize);
    if (fieldsAreNull) {
      return log.incorrectContent(methodeName);
    } else {
      return personService.addPerson(personDeserialize, methodeName);
    }
  }

  /**
   * Some javadoc.
   * 
   * Updates an existing person based on their first and last name.
   *
   * @param firstName of the person.
   * @param lastName  of the person.
   */
  @PutMapping("/{firstName}/{lastName}")
  public ResponseEntity<String> updatePersonByFirstAndLastName(@PathVariable("firstName") String firstName,
      @PathVariable("lastName") String lastName, @RequestBody PersonDeserialization personDeserialize) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName, firstName, lastName);

    // Check the request content :
    Boolean areFieldsAreNull = beanService.areFieldsNullExceptId(personDeserialize);
    if (areFieldsAreNull) {
      return log.incorrectContent(methodeName);
    } else {
      return personService.updateByFirstAndLastName(firstName, lastName, personDeserialize, methodeName);
    }
  }

  /**
   * Some javadoc.
   * 
   * Deletes a person based on their first and last name.
   *
   * @param firstName of the person.
   * @param lastName  of the person.
   */
  @Transactional
  @DeleteMapping("/{firstName}/{lastName}")
  public ResponseEntity<String> deleteByFirstAndLastName(@PathVariable("firstName") String firstName,
      @PathVariable("lastName") String lastName) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);

    return personService.deleteByFirstAndLastName(firstName, lastName, methodeName);
  }

  /**
   * Some javadoc.
   * 
   * Get all person objetcs.
   *
   */
  @GetMapping("/all")
  public List<Person> getAllPersons() {
    return personService.getAllPersons();
  }
}