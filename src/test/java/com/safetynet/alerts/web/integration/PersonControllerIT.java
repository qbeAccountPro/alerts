package com.safetynet.alerts.web.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.web.controller.PersonController;
import com.safetynet.alerts.web.deserialization.model.PersonDeserialization;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.service.HouseHoldService;
import com.safetynet.alerts.web.service.PersonService;

@SpringBootTest
public class PersonControllerIT {
  private MockMvc mvc;

  @Autowired
  private PersonService personService;

  @Autowired
  private HouseHoldService houseHoldService;

  String firstName = "Quentin", lastName = "Beraud", address = "Rue d'ici";
  String city = "Lyon", email = "qbe.pro@yahoo.com", phone = "06 66 66 66 66", zip = "69000";

  @BeforeEach
  public void setUp() {
    this.mvc = MockMvcBuilders
        .standaloneSetup(
            new PersonController(personService))
        .setControllerAdvice()
        .build();

    Person person = personService.getPersonByFirstAndLastName(firstName, lastName);
    if (person != null) {
      personService.deleteByFirstAndLastName(firstName, lastName, "deletePersonITTest");
    }
  }

  @Test
  public void addPersonTest() throws Exception {
    // Add a new Person
    PersonDeserialization newPerson = new PersonDeserialization();
    newPerson.setFirstName(firstName);
    newPerson.setLastName(lastName);
    newPerson.setAddress(address);
    newPerson.setCity(city);
    newPerson.setEmail(email);
    newPerson.setPhone(phone);
    newPerson.setZip(zip);

    // Person on JSON
    ObjectMapper objectMapper = new ObjectMapper();
    String personJson = objectMapper.writeValueAsString(newPerson);

    // Send POST request to add the firestation
    mvc.perform(MockMvcRequestBuilders.post("/person")
        .contentType(MediaType.APPLICATION_JSON)
        .content(personJson))
        .andExpect(status().isCreated());

    // Check if the person exists and match

    List<Person> allPersons = personService.getAllPersons();

    Household matchingHousehold = houseHoldService.getHouseholdByAddress(address);
    boolean personAdded = false;
    for (Person person : allPersons) {
      if (person.getFirstName().equals(firstName)
          && person.getLastName().equals(lastName)
          && person.getIdHousehold() == matchingHousehold.getId()
          && person.getCity().equals(city)
          && person.getEmail().equals(email)
          && person.getPhone().equals(phone)
          && person.getZip().equals(zip)) {
        personAdded = true;
        break;
      }
    }

    // Check the successfully added person
    assertTrue(personAdded);
  }

  @Test
  public void updatePersonByFirstAndLastNameTest() throws Exception {
    // Add a new Person
    addPersonTest();

    // Set an updated Peson
    PersonDeserialization updatePerson = new PersonDeserialization();
    updatePerson.setFirstName(firstName);
    updatePerson.setLastName(lastName);
    updatePerson.setAddress("pas là");
    updatePerson.setCity("CarroteCity");
    updatePerson.setEmail(email);
    updatePerson.setPhone(phone);
    updatePerson.setZip("00");

    // Person on JSON
    ObjectMapper objectMapper = new ObjectMapper();
    String updatePersonJson = objectMapper.writeValueAsString(updatePerson);

    // Send PUT request to update a person
    mvc.perform(MockMvcRequestBuilders.put("/person/{firstName}/{lastName}", firstName, lastName)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatePersonJson))
        .andExpect(status().isOk());

    // Check if the person already exists and match
    List<Person> allPersons = personService.getAllPersons();
    Household matchingHousehold = houseHoldService.getHouseholdByAddress("pas là");
    boolean personAdded = false;
    for (Person person : allPersons) {
      if (person.getFirstName().equals(firstName)
          && person.getLastName().equals(lastName)
          && person.getIdHousehold() == matchingHousehold.getId()
          && person.getCity().equals("CarroteCity")
          && person.getEmail().equals(email)
          && person.getPhone().equals(phone)
          && person.getZip().equals("00")) {
        personAdded = true;
        break;
      }
    }

    // Check the successfully updated person
    assertTrue(personAdded);

  }

  @Test
  public void deleteByFirstAndLastNameTest() throws Exception {
    // Add a new Person
    addPersonTest();

    // Send DELETE request to delete Person by First and Last Name
    mvc.perform(MockMvcRequestBuilders.delete("/person/{firstName}/{lastName}", firstName, lastName))
        .andExpect(status().isOk());

    // Check if the person does not exist
    List<Person> allPersons = personService.getAllPersons();
    boolean personAdded = true;
    for (Person person : allPersons) {
      if (person.getFirstName().equals(firstName)
          && person.getLastName().equals(lastName)) {
        personAdded = false;
        break;
      }
    }

    // Check the successfully delete person
    assertTrue(personAdded);
  }

  @Test
  public void getAllPersonsTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/person/all")).andExpect(status().isOk());
  }
}