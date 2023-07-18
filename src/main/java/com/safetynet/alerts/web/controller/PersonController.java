package com.safetynet.alerts.web.controller;

import java.net.URI; 
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.web.bind.annotation.*;

import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.service.BeanService;
import com.safetynet.alerts.web.service.PersonService;

//@CrossOrigin("*")
@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("")
    public List<Person> list() {
        return personService.getAllPersons();
    }

    @GetMapping(value = "/{id}")
    public Person findById(@PathVariable int id) {
        return personService.getPersonById(id);
    }

    @PutMapping(value = "")
    public void updateProduit(@RequestBody Person person) {
        personService.savePerson(person);
    }

    @PutMapping("/{firstName}/{lastName}")
    public void updateByFirstNameAndLastName(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName, @RequestBody Person newPerson) {
        Person oldPerson = personService.findPersonByFirstNameAndLastName(firstName, lastName);
        if (oldPerson != null) {
            try {
                Person updatePerson = BeanService.updateBeanWithNotNullPropertiesFromNewObject(oldPerson, newPerson);
                updatePerson.setId(oldPerson.getId());
                updatePerson.setFirstName(firstName);
                updatePerson.setFirstName(lastName);
                personService.savePerson(updatePerson);
            } catch (Exception e) {
                System.out.println("updateByFirstNameAndLastName produit l'erreur : " + e);
            }
        }
 
    }

    @PostMapping(value = "")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        Person personsAdded = personService.savePerson(person);
        if (Objects.isNull(personsAdded)) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(personsAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable int id) {
        personService.deletePersonById(id);
    }

    @Transactional 
    @DeleteMapping("/{firstName}/{lastName}")
    public void deletePerson(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
        personService.findPersonByFirstNameAndLastName(firstName, lastName);
    }
}
