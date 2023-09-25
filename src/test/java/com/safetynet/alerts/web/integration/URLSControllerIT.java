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
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.safetynet.alerts.web.controller.URLSController;
import com.safetynet.alerts.web.dao.FirestationDao;
import com.safetynet.alerts.web.dao.HouseholdDao;
import com.safetynet.alerts.web.dao.MedicalRecordDao;
import com.safetynet.alerts.web.dao.PersonDao;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.Serialization;
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
  PersonDao personDao;

  @Autowired
  HouseholdDao householdDao;

  @Autowired
  MedicalRecordDao medicalRecordDao;

  @Autowired
  FirestationDao firestationDao;

  // Data to creates examples :
  String address = "Rue du loup 8899", firstName = "Quentin", lastName = "Beraud", city = "Lyon",
      email = "qbe@yahoo.com", phone = "06 66 66 66 66", zip = "69020", station = "99Y";

  @BeforeAll
  public void setup() {
    HouseHoldService houseHoldService = new HouseHoldService(householdDao);
    FirestationService firestationService = new FirestationService(firestationDao, houseHoldService);
    PersonService personService = new PersonService(personDao, houseHoldService, medicalRecordDao);
    MedicalRecordService medicalRecordService = new MedicalRecordService(medicalRecordDao, personService);
    Serialization serialization = new Serialization();
    mvc = MockMvcBuilders
        .standaloneSetup(new URLSController(
            new URLSService(firestationService, personService, medicalRecordService, serialization, houseHoldService)))
        .setControllerAdvice()
        .build();

    // Create and save correponding example object to test URLS request
    // Create Household
    Household household = new Household();
    household.setAddress(address);
    // Save it
    householdDao.save(household);
    // Get the corresponding id
    household = householdDao.findByAddress(address);

    // Create Person
    Person person = new Person();
    person.setFirstName(firstName);
    person.setLastName(lastName);
    person.setIdHousehold(household.getId());
    person.setCity(city);
    person.setEmail(email);
    person.setPhone(phone);
    person.setZip(zip);
    // Save it
    personDao.save(person);
    // Get corresponding id
    person = personDao.findByFirstNameAndLastName(firstName, lastName);

    // Create MedicalRecord
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setIdPerson(person.getId());
    medicalRecord.setMedications(null);
    medicalRecord.setAllergies(null);
    medicalRecord.setBirthdate("02/02/1980");
    // Save it
    medicalRecordDao.save(medicalRecord);

    // Create Firestation
    Firestation firestation = new Firestation();
    List<Integer> idHouseholds = Arrays.asList(household.getId());
    firestation.setIdHouseholds(idHouseholds);
    firestation.setStation(station);
    // Save it
    firestationDao.save(firestation);
  }

  @Test
  public void getPersonCoveredByFirestationTest() throws Exception {
    // Send request to get person covered by firestation :
    mvc.perform(MockMvcRequestBuilders.get("/firestation?stationNumber=" + station)).andExpect(status().isOk());

    // Get content file :
    String filePath = getMostRecentFilePathForEachMethode("personCoveredByFireStation_" + station + ".json");
    String fileContent = getFileContent(filePath);

    // Check if the data contains the corresponding values :
    assertTrue(fileContent.contains(firstName));
    assertTrue(fileContent.contains(lastName));
    assertTrue(fileContent.contains(address));
    assertTrue(fileContent.contains(phone));
    assertTrue(fileContent.contains("\"adults\" : 1"));
    assertTrue(fileContent.contains("\"minors\" : 0"));
  }

  @Test
  public void getChildrenLivingAtThisAddressTest() throws Exception {
    // Send request to get children living at one address
    mvc.perform(MockMvcRequestBuilders.get("/childAlert?address=" + address)).andExpect(status().isOk());

    // Get content file :
    String filePath = getMostRecentFilePathForEachMethode("childrenLivingAtThisAddress_" + address + ".json");
    String fileContent = getFileContent(filePath);

    // Check if the data contains the corresponding values :
    assertTrue(fileContent.contains("\"children\" : [ ]"));
    assertTrue(fileContent.contains("\"adults\" : [ {"));
    assertTrue(fileContent.contains("\"firstName\" : \"" + firstName + "\""));
    assertTrue(fileContent.contains("\"lastName\" : \"" + lastName + "\""));
  }

  @Test
  public void getPersonsPhoneNumbersCoveredByStationTest() throws Exception {
    // Send request to get phone number by firestation
    mvc.perform(MockMvcRequestBuilders.get("/phoneAlert?firestation=" + station)).andExpect(status().isOk());

    // Get content file :
    String filePath = getMostRecentFilePathForEachMethode("personsPhoneNumbersCoveredByStation_" + station + ".json");
    String fileContent = getFileContent(filePath);

    // Check if the data contains the corresponding values :
    assertTrue(fileContent.contains("\"phone\" : \"" + phone + "\""));
  }

  @Test
  public void getStationAndPersonsByAddressTest() throws Exception {
    // Send request to get station and person by address
    mvc.perform(MockMvcRequestBuilders.get("/fire?address=" + address)).andExpect(status().isOk());

    // Get content file :
    String filePath = getMostRecentFilePathForEachMethode("stationAndPersonsByAddress_" + address + ".json");
    String fileContent = getFileContent(filePath);

    // Check if the data contains the corresponding values :
    assertTrue(fileContent.contains("\"station\" : \"" + station + "\""));
    assertTrue(fileContent.contains("\"lastName\" : \"" + lastName + "\""));
    assertTrue(fileContent.contains("\"phone\" : \"" + phone + "\""));
    assertTrue(fileContent.contains("\"medications\" : [ ]"));
    assertTrue(fileContent.contains("\"allergies\" : [ ]"));

  }

  @Test
  public void getPersonsByHouseholdsFromStationTest() throws Exception {
    // Send POST request to add the firestation
    mvc.perform(MockMvcRequestBuilders.get("/flood/stations?stations=" + station)).andExpect(status().isOk());

    // Get content file :
    String filePath = getMostRecentFilePathForEachMethode("personsByHouseholdsFromStation_" + station + ".json");
    String fileContent = getFileContent(filePath);

    // Check if the data contains the corresponding values :
    assertTrue(fileContent.contains("\"address\" : \"" + address + "\""));
    assertTrue(fileContent.contains("\"lastName\" : \"" + lastName + "\""));
    assertTrue(fileContent.contains("\"phone\" : \"" + phone + "\""));
    assertTrue(fileContent.contains("\"medications\" : [ ]"));
    assertTrue(fileContent.contains("\"allergies\" : [ ]"));
  }

  @Test
  public void getPersonInfoByFirstAndLastNameTest() throws Exception {
    // Send POST request to add the firestation
    mvc.perform(MockMvcRequestBuilders.get("/personInfo?firstName=" + firstName + "&lastName=" + lastName))
        .andExpect(status().isOk());

    // Get content file :
    String filePath = getMostRecentFilePathForEachMethode(
        "personInfoByFirstAndLastName_" + firstName + "_" + lastName + ".json");
    String fileContent = getFileContent(filePath);

    // Check if the data contains the corresponding values :
    assertTrue(fileContent.contains("\"address\" : \"" + address + "\""));
    assertTrue(fileContent.contains("\"lastName\" : \"" + lastName + "\""));
    assertTrue(fileContent.contains("\"email\" : \"" + email + "\""));
    assertTrue(fileContent.contains("\"medications\" : [ ]"));
    assertTrue(fileContent.contains("\"allergies\" : [ ]"));
  }

  @Test
  public void getAllResidentsEmailsTest() throws Exception {
    // Send POST request to add the firestation
    mvc.perform(MockMvcRequestBuilders.get("/communityEmail?city=" + city)).andExpect(status().isOk());

    // Get content file :
    String filePath = getMostRecentFilePathForEachMethode("allResidentsEmailsFromCity_" + city + ".json");
    String fileContent = getFileContent(filePath);

    // Check if the data contains the corresponding values :
    assertTrue(fileContent.contains("\"email\" : \"" + email + "\""));
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

  private String getFileContent(String filePath) {
    try (FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader)) {
      StringBuilder fileContent = new StringBuilder();
      String line;

      while ((line = bufferedReader.readLine()) != null) {
        fileContent.append(line);
      }

      String content = fileContent.toString();
      return content;
    } catch (IOException e) {
      return null;
    }
  }
}