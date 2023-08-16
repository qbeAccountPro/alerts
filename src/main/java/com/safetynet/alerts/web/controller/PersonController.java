package com.safetynet.alerts.web.controller;

import org.tinylog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Updates an existing person based on their first name and last name.
     *
     * @param firstName The first name of the person to be updated.
     * @param lastName  The last name of the person to be updated.
     * @param newPerson The updated Person object with the new information.
     */
    @PutMapping("/{firstName}/{lastName}")
    public void updateByFirstNameAndLastName(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName, @RequestBody Person newPerson) {
        Person oldPerson = personService.findPersonByFirstNameAndLastName(firstName, lastName);
        if (oldPerson != null) {
            try {
                Person updatePerson = BeanService.updateBeanWithNotNullPropertiesFromNewObject(oldPerson, newPerson);
                updatePerson.setId(oldPerson.getId());
                updatePerson.setFirstName(firstName);
                updatePerson.setLastName(lastName);
                Logger.info("Request : " + BeanService.getCurrentMethodName() + " with firstName and lastName : " + firstName
                        + " " + lastName + ".");
                personService.savePerson(updatePerson);
            } catch (Exception e) {
                Logger.error("Request : " + BeanService.getCurrentMethodName() + " with firstName and lastName : " + firstName
                        + " " + lastName + ", genereted this exception : " + e);
            }
        } else {
            Logger.error("Request : " + BeanService.getCurrentMethodName() + " with firstName and lastName : " + firstName
                    + " " + lastName + ", doesn't match with any Person.");
        }
    }

    /**
     * Some javadoc.
     * Adds a new person to the system.
     *
     * @param person The Person object representing the new person to be added.
     */
    @PostMapping(value = "")
    public void addPerson(@RequestBody Person person) {
        Logger.info("Request : " + BeanService.getCurrentMethodName() + "().");
        if (beanService.areAllFieldsNullExceptId(person)) {
            Logger.error("Answer " + BeanService.getCurrentMethodName() + " : person is null.");
        } else {
            personService.savePerson(person);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : Person created with succes.");
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
    public void deleteByFirstNameAndLastName(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName) {
        personService.deletePersonByFirstNameAndLastName(firstName, lastName);
        Logger.info("Request : " + BeanService.getCurrentMethodName() + "() with firstName and lastName : " + firstName
                + " " + lastName + ".");
    }
}
