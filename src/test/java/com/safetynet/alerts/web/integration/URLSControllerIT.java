package com.safetynet.alerts.web.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.safetynet.alerts.web.controller.URLSController;
import com.safetynet.alerts.web.deserialization.model.FirestationDeserialization;
import com.safetynet.alerts.web.deserialization.model.PersonDeserialization;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.service.FirestationService;
import com.safetynet.alerts.web.service.HouseHoldService;
import com.safetynet.alerts.web.service.MedicalRecordService;
import com.safetynet.alerts.web.service.PersonService;
import com.safetynet.alerts.web.service.URLSService;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class URLSControllerIT {
  private MockMvc mvc;

  @Autowired
  PersonService personService;

  @Autowired
  HouseHoldService houseHoldService;

  @Autowired
  MedicalRecordService medicalRecordService;

  @Autowired
  FirestationService firestationService;

  @Autowired
  URLSService urlsService;

  // Data to creates examples :
  String address = "Rue du loup 8899", firstName = "Quentin", lastName = "Beraud", city = "Lyon",
      email = "qbe@yahoo.com", phone = "06 66 66 66 66", zip = "69020", station = "99Y";

  @BeforeAll
  public void setup() {
    mvc = MockMvcBuilders
        .standaloneSetup(new URLSController(urlsService))
        .setControllerAdvice()
        .build();

    // Create and save correponding example object to test URLS request
    // Create Household
    Household household = new Household();
    household.setAddress(address);
    // Save it
    houseHoldService.saveHousehold(address);
    // Get the corresponding id
    household = houseHoldService.getHouseholdByAddress(address);

    // Create Person
    PersonDeserialization personDeserialization = new PersonDeserialization();
    personDeserialization.setFirstName(firstName);
    personDeserialization.setLastName(lastName);
    personDeserialization.setAddress(address);
    personDeserialization.setCity(city);
    personDeserialization.setEmail(email);
    personDeserialization.setPhone(phone);
    personDeserialization.setZip(zip);
    // Save it
    personService.addPerson(personDeserialization, "addPersonITtest");
    // Get corresponding id
    Person person = personService.getPersonByFirstAndLastName(firstName, lastName);

    // Create MedicalRecord
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setIdPerson(person.getId());
    medicalRecord.setMedications(null);
    medicalRecord.setAllergies(null);
    medicalRecord.setBirthdate("02/02/1980");
    // Save it
    medicalRecordService.saveMedicalRecord(medicalRecord);

    // Create Firestation
    FirestationDeserialization firestationDeserialization = new FirestationDeserialization();
    firestationDeserialization.setStation(station);
    firestationDeserialization.setAddress(household.getAddress());

    // Save it
    firestationService.addFirestation(firestationDeserialization, "addFirestationITtest");
  }

  @Test
  public void getPersonCoveredByFirestationTest() throws Exception {
    // Send request to get person covered by firestation :
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber=" + station))
        .andExpect(status().isOk()).andReturn();


    // Extract the JSON content from the response:
    String responseContent = result.getResponse().getContentAsString();

    // Check if the data contains the corresponding values :
    assertTrue(responseContent.contains(firstName));
    assertTrue(responseContent.contains(lastName));
    assertTrue(responseContent.contains(address));
    assertTrue(responseContent.contains(phone));
    assertTrue(responseContent.contains("\"adults\":1"));
    assertTrue(responseContent.contains("\"minors\":0"));
  }

  @Test
  public void getChildrenLivingAtThisAddressTest() throws Exception {
    // Send request to get children living at one address
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/childAlert?address=" + address)).andExpect(status().isOk()).andReturn();

    // Extract the JSON content from the response:
    String responseContent = result.getResponse().getContentAsString();

    // Check if the data contains the corresponding values :
    assertTrue(responseContent.contains("\"children\":[]"));
    assertTrue(responseContent.contains("\"adults\":[{"));
    assertTrue(responseContent.contains("\"firstName\":\"" + firstName + "\""));
    assertTrue(responseContent.contains("\"lastName\":\"" + lastName + "\""));
  }

  @Test
  public void getPersonsPhoneNumbersCoveredByStationTest() throws Exception {
    // Send request to get phone number by firestation
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/phoneAlert?firestation=" + station)).andExpect(status().isOk()).andReturn();

    // Extract the JSON content from the response:
    String responseContent = result.getResponse().getContentAsString();

    // Check if the data contains the corresponding values :
    assertTrue(responseContent.contains("\"phone\":\"" + phone + "\""));
  }

  @Test
  public void getStationAndPersonsByAddressTest() throws Exception {
    // Send request to get station and person by address
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/fire?address=" + address)).andExpect(status().isOk()).andReturn();

    // Extract the JSON content from the response:
    String responseContent = result.getResponse().getContentAsString();

    // Check if the data contains the corresponding values :
    assertTrue(responseContent.contains("\"station\":\"" + station + "\""));
    assertTrue(responseContent.contains("\"lastName\":\"" + lastName + "\""));
    assertTrue(responseContent.contains("\"phone\":\"" + phone + "\""));
    assertTrue(responseContent.contains("\"medications\":[]"));
    assertTrue(responseContent.contains("\"allergies\":[]"));

  }

  @Test
  public void getPersonsByHouseholdsFromStationTest() throws Exception {
    // Send POST request to add the firestation
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/flood/stations?stations=" + station)).andExpect(status().isOk()).andReturn();

    // Extract the JSON content from the response:
    String responseContent = result.getResponse().getContentAsString();

    // Check if the data contains the corresponding values :
    assertTrue(responseContent.contains("\"address\":\"" + address + "\""));
    assertTrue(responseContent.contains("\"lastName\":\"" + lastName + "\""));
    assertTrue(responseContent.contains("\"phone\":\"" + phone + "\""));
    assertTrue(responseContent.contains("\"medications\":[]"));
    assertTrue(responseContent.contains("\"allergies\":[]"));
  }

  @Test
  public void getPersonInfoByFirstAndLastNameTest() throws Exception {
    // Send POST request to add the firestation
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName=" + firstName + "&lastName=" + lastName))
        .andExpect(status().isOk()).andReturn();

    // Extract the JSON content from the response:
    String responseContent = result.getResponse().getContentAsString();

    // Check if the data contains the corresponding values :
    assertTrue(responseContent.contains("\"lastName\":\"" + lastName + "\""));
    assertTrue(responseContent.contains("\"email\":\"" + email + "\""));
    assertTrue(responseContent.contains("\"medications\":[]"));
    assertTrue(responseContent.contains("\"allergies\":[]"));
  }

  @Test
  public void getAllResidentsEmailsTest() throws Exception {
    // Send POST request to add the firestation
    MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/communityEmail?city=" + city)).andExpect(status().isOk()).andReturn();

    // Extract the JSON content from the response:
    String responseContent = result.getResponse().getContentAsString();

    // Check if the data contains the corresponding values :
    assertTrue(responseContent.contains("\"email\":\"" + email + "\""));
  }

  public String getMostRecentFilePathForEachMethode(String methodNameWithValue) {
    // Get the diretory path
    String projectDirectory = System.getProperty("user.dir");

    // Get list of files inside the directory
    File directory = new File(projectDirectory);
    FilenameFilter filter = (dir, name) -> name.endsWith(methodNameWithValue);
    File[] matchingFiles = directory.listFiles(filter);

    // Check if there are corresponding files
    if (matchingFiles != null && matchingFiles.length > 0) {

      // Triez les fichiers par date de modification pour obtenir le plus récent en
      // premier
      Arrays.sort(matchingFiles, Comparator.comparingLong(File::lastModified).reversed());

      // The most recent file take the first place then affect it
      File mostRecentFile = matchingFiles[0];

      // Return the most recent file corresponding
      return mostRecentFile.getAbsolutePath();
    } else {
      return null;
    }
  }
}