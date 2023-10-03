package com.safetynet.alerts.web.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
import com.safetynet.alerts.web.controller.MedicalRecordController;
import com.safetynet.alerts.web.deserialization.model.MedicalRecordDeserialization;
import com.safetynet.alerts.web.deserialization.model.PersonDeserialization;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.service.HouseHoldService;
import com.safetynet.alerts.web.service.MedicalRecordService;
import com.safetynet.alerts.web.service.PersonService;

@SpringBootTest
public class MedicalRecordControllerIT {

  private MockMvc mvc;

  @Autowired
  PersonService personService;

  @Autowired
  MedicalRecordService medicalRecordService;

  @Autowired
  HouseHoldService houseHoldService;

  String firstName = "Quentin", lastName = "Beraud", birthdate = "12/31/1997";
  List<String> medications = new ArrayList<>(), allergies = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    this.mvc = MockMvcBuilders
        .standaloneSetup(new MedicalRecordController(medicalRecordService))
        .setControllerAdvice()
        .build();

    Person person = personService.getPersonByFirstAndLastName(firstName, lastName);
    if (person == null) {
      // Save a coresponding person
      PersonDeserialization personDeserialization = new PersonDeserialization();
      personDeserialization.setFirstName(firstName);
      personDeserialization.setLastName(lastName);
      personService.addPerson(personDeserialization, "testMethodeITPersonAdd");
      person = personService.getPersonByFirstAndLastName(firstName, lastName);

    }
    MedicalRecord medicalRecord = medicalRecordService.getMedicalRecordByPerson(person);
    if (medicalRecord != null) {
      medicalRecordService.deleteMedicalRecord(firstName, lastName, "testMethodeITdeleteMedicalRecord");
    }
  }

  @Test
  public void addMedicalRecordTest() throws Exception {

    // New MedicalRecord added
    MedicalRecordDeserialization newMedicalRecord = new MedicalRecordDeserialization();
    newMedicalRecord.setFirstName(firstName);
    newMedicalRecord.setLastName(lastName);
    newMedicalRecord.setBirthdate(birthdate);
    newMedicalRecord.setMedications(medications);
    newMedicalRecord.setAllergies(allergies);

    // MedicalRecord on JSON
    ObjectMapper objectMapper = new ObjectMapper();
    String newMedicalRecordJson = objectMapper.writeValueAsString(newMedicalRecord);

    // Send POST request to add the MedicalRecord
    mvc.perform(MockMvcRequestBuilders.post("/medicalRecord")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newMedicalRecordJson))
        .andExpect(status().isCreated());

    // Check if the medicalRecord exists and match

    List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
    Person matchingPerson = personService.getPersonByFirstAndLastName(firstName, lastName);
    boolean medicalRecordAdded = false;
    for (MedicalRecord medicalRecord : medicalRecords) {
      if (medicalRecord.getIdPerson() == matchingPerson.getId()
          && medicalRecord.getBirthdate().equals(birthdate)
          && medicalRecord.getMedications().equals(medications)
          && medicalRecord.getAllergies().equals(allergies)) {
        medicalRecordAdded = true;
        break;
      }
    }

    // Check the successfully added Medical Record
    assertTrue(medicalRecordAdded);
  }

  @Test
  public void updateMedicalRecordTest() throws Exception {
    // Add MedicalRecord
    addMedicalRecordTest();

    // New MedicalRecord updated
    MedicalRecordDeserialization updatedMedicalRecord = new MedicalRecordDeserialization();
    updatedMedicalRecord.setFirstName(firstName);
    updatedMedicalRecord.setLastName(lastName);
    updatedMedicalRecord.setBirthdate("02/25/1999");
    updatedMedicalRecord.setMedications(medications);
    updatedMedicalRecord.setAllergies(allergies);

    // MedicalRecord on JSON
    ObjectMapper objectMapper = new ObjectMapper();
    String updatedMedicalRecordJson = objectMapper.writeValueAsString(updatedMedicalRecord);

    // Send PUT request to update a medicalRecord
    mvc.perform(MockMvcRequestBuilders.put("/medicalRecord/{firstName}/{lastName}", firstName, lastName)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatedMedicalRecordJson))
        .andExpect(status().isOk());

    // Checks if the firestation has updated
    List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
    Person matchingPerson = personService.getPersonByFirstAndLastName(firstName, lastName);
    boolean medicalRecordUpdated = false;
    for (MedicalRecord medicalRecord : medicalRecords) {
      if (medicalRecord.getIdPerson() == matchingPerson.getId() && medicalRecord.getBirthdate().equals("02/25/1999")) {
        medicalRecordUpdated = true;
        break;
      }
    }

    // Check the successfully added firestation
    assertTrue(medicalRecordUpdated);
  }

  @Test
  public void deleteMedicalRecordTest() throws Exception {
    // Add MedicalRecord
    addMedicalRecordTest();

    // Send DELETE request to delete MedicalRecord by First and Last Name
    mvc.perform(MockMvcRequestBuilders.delete("/medicalRecord/{firstName}/{lastName}", firstName, lastName))
        .andExpect(status().isOk());

    // Check if the medicalRecord has been deleted
    List<MedicalRecord> medicalRecords = medicalRecordService.getAllMedicalRecords();
    Person matchingPerson = personService.getPersonByFirstAndLastName(firstName, lastName);
    boolean medicalRecordDeleted = true;
    for (MedicalRecord medicalRecord : medicalRecords) {
      if (medicalRecord.getIdPerson() == matchingPerson.getId()) {
        medicalRecordDeleted = false;
      }
    }

    // Check the successfully deleted medicalRecord
    assertTrue(medicalRecordDeleted);
  }

  @Test
  public void getAllMedicalRecordsTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/medicalRecord/all")).andExpect(status().isOk());
  }
}
