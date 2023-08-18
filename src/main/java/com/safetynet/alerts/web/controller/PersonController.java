package com.safetynet.alerts.web.controller;

import org.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.service.BeanService;
import com.safetynet.alerts.web.service.PersonService;

/**
 * Some javadoc.
 * This class represents a REST controller for managing persons.
 * It provides endpoints for retrieving all persons, finding a person by ID,
 * adding new persons, updating existing persons, and deleting persons.
 */
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private final PersonService personService;
    private final BeanService beanService;

    public PersonController(PersonService personService) {
        this.personService = personService;
        this.beanService = new BeanService();
    }

    /**
     * Some javadoc.
     * Adds a new person to the system.
     *
     * @param person The Person object representing the new person to be added.
     */
    @PostMapping(value = "")
    public ResponseEntity<String> addPerson(@RequestBody Person person) {
        Logger.info("Request " + BeanService.getCurrentMethodName() + ".");
        Boolean areFieldsAreNull = beanService.areFieldsNullExceptId(person);
        if (areFieldsAreNull) {
            Logger.error("Answer " + BeanService.getCurrentMethodName() + " : content is incorrect.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person content is incorrect.");
        } else {
            personService.savePerson(person);

            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : person added successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body("Person added successfully.");
        }
    }

    /**
     * Some javadoc.
     * Updates an existing person based on their first name and last name.
     *
     * @param firstName The first name of the person to be updated.
     * @param lastName  The last name of the person to be updated.
     * @param newPerson The updated Person object with the new information.
     */
    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<String> updateByFirstNameAndLastName(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName, @RequestBody Person newPerson) {
        Logger.info("Request " + BeanService.getCurrentMethodName() + " : with this first and last name = " + firstName
                + " "
                + lastName + ".");
        Boolean areFieldsAreNull = beanService.areFieldsNullExceptId(newPerson);
        if (areFieldsAreNull) {
            Logger.error("Answer " + BeanService.getCurrentMethodName() + " : content is incorrect.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person content is incorrect.");
        } else {
            Person oldPerson = personService.findPersonByFirstNameAndLastName(firstName, lastName);
            if (oldPerson != null) {
                try {
                    Person updatePerson = BeanService.updateBeanWithNotNullPropertiesFromNewObject(oldPerson,
                            newPerson);
                    updatePerson.setId(oldPerson.getId());
                    updatePerson.setFirstName(firstName);
                    updatePerson.setLastName(lastName);
                    personService.savePerson(updatePerson);
                    Logger.info(
                            "Answer " + BeanService.getCurrentMethodName() + " : modified successfully.");
                    return ResponseEntity.status(HttpStatus.OK).body("Person modified successfully.");
                } catch (Exception e) {
                    Logger.error("Answer " + BeanService.getCurrentMethodName() + " : threw an exception.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request threw an exception.");
                }
            } else {
                Logger.error("Answer " + BeanService.getCurrentMethodName() + " : first and last name doesn't match.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("First and last name doesn't match.");
            }
        }
    }

    /**
     * Some javadoc.
     * Deletes a person based on their first name and last name.
     *
     * @param firstName The first name of the person to be deleted.
     * @param lastName  The last name of the person to be deleted.
     */
    @Transactional
    @DeleteMapping("/{firstName}/{lastName}")
    public ResponseEntity<String> deleteByFirstNameAndLastName(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName) {
        Logger.info(
                "Request " + BeanService.getCurrentMethodName() + " : with this first and last name  = " + firstName
                        + " " + lastName + ".");
        Person oldPerson = personService.findPersonByFirstNameAndLastName(firstName, lastName);
        if (oldPerson != null) {
            personService.deletePersonByFirstNameAndLastName(firstName, lastName);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body("Person deleted successfully.");
        } else {
            Logger.error("Answer " + BeanService.getCurrentMethodName() + " : doesn't match with any person.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person address doesn't match.");
        }
     
    }
}
