package com.safetynet.alerts.web.serialization;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.web.dao.FirestationDao;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.dao.ChildAlertDao;
import com.safetynet.alerts.web.serialization.dao.CommunityEmailDao;
import com.safetynet.alerts.web.serialization.dao.FireAlertDao;
import com.safetynet.alerts.web.serialization.dao.OtherResidentsDao;
import com.safetynet.alerts.web.serialization.dao.PersonInfoDao;
import com.safetynet.alerts.web.serialization.dao.PhoneAlertDao;
import com.safetynet.alerts.web.serialization.model.ChildAlert;
import com.safetynet.alerts.web.serialization.model.FireAlert;
import com.safetynet.alerts.web.serialization.model.FirestationAlert;
import com.safetynet.alerts.web.serialization.model.FloodAlert;
import com.safetynet.alerts.web.serialization.model.FloodAlertByHousehold;
import com.safetynet.alerts.web.serialization.model.PersonInfoAlert;

@ExtendWith(MockitoExtension.class)
public class SerializationTest {

  @Mock
  private ChildAlertDao childAlertDao;
  @Mock
  private CommunityEmailDao communityEmailDao;
  @Mock
  private FireAlertDao FireAlertDao;
  @Mock
  private FirestationDao firestationDao;
  @Mock
  private OtherResidentsDao otherResidentsDao;
  @Mock
  private PersonInfoDao personeInfoDao;
  @Mock
  private PhoneAlertDao phoneAlertDao;

  @InjectMocks
  private Serialization serialization;

  private Person person_1, person_2;
  private List<Person> persons;
  private String method, argument;

  @BeforeEach
  public void setUp() {

    person_1 = new Person(1, 1, "Quentin", "Beraud", "Lyon", "69000", "06 00 00 00 00", "qbe@yahoo.com");
    person_2 = new Person(2, 1, "Jack", "Land", "Paris", "75000", "00 00 00 00 00", "Jack@yahoo.com");
    persons = Arrays.asList(person_1, person_2);
    method = "someMethod";
    argument = "someArgument";
  }

  @Test
  void testChildAlertSerialization() {
    ChildAlert childAlert_1 = new ChildAlert(person_1.getFirstName(), person_1.getLastName(), 25);
    ChildAlert childAlert_2 = new ChildAlert(person_2.getFirstName(), person_2.getLastName(), 10);
    List<ChildAlert> children = Arrays.asList(childAlert_2);
    List<ChildAlert> adults = Arrays.asList(childAlert_1);

    serialization.childAlertSerialization(children, adults, method, argument);

    String fileName = serialization.generateFileName(method, argument);
    File file = new File(fileName);
    assertTrue(new File(fileName).exists());
    try {
      String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
      assertTrue(fileContent.contains("children"));
      assertTrue(fileContent.contains(person_1.getFirstName()));
      assertTrue(fileContent.contains(person_2.getLastName()));
      assertTrue(fileContent.contains("adults"));
      file.delete();
      assertFalse(file.exists());
    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }

  @Test
  void testCommunityEmailSerialization() {
    serialization.communityEmailSerialization(persons, method, argument);

    String fileName = serialization.generateFileName(method, argument);
    File file = new File(fileName);
    assertTrue(new File(fileName).exists());
    try {
      String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
      assertTrue(fileContent.contains("emails"));
      assertTrue(fileContent.contains(person_1.getEmail()));
      assertTrue(fileContent.contains(person_2.getEmail()));
      file.delete();
      assertFalse(file.exists());
    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }

  @Test
  void testEmptyAnswer() {
    serialization.emptyAnswer(method, argument);

    String fileName = serialization.generateFileName(method, argument);
    File file = new File(fileName);
    assertTrue(new File(fileName).exists());
    try {
      String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
      assertTrue(fileContent.isEmpty());
      file.delete();
      assertFalse(file.exists());
    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }

  @Test
  void testFireSerialization() {
    FireAlert fire_1 = new FireAlert("Moti", "06 66 66 66 66", 35, null, null);
    List<FireAlert> fires = Arrays.asList(fire_1);
    String firestationNumber = "1";

    serialization.fireSerialization(fires, firestationNumber, method, argument);

    String fileName = serialization.generateFileName(method, argument);
    File file = new File(fileName);
    assertTrue(new File(fileName).exists());
    try {
      String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
      assertTrue(fileContent.contains("persons"));
      assertTrue(fileContent.contains("station"));
      assertTrue(fileContent.contains("stationServing"));
      assertTrue(fileContent.contains("Moti"));
      file.delete();
      assertFalse(file.exists());
    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }

  @Test
  void testFirestationAlertSerialization() {
    FirestationAlert firestationAlert = new FirestationAlert("Quentin", "Beraud", "Rue d'ici", "000000");
    List<FirestationAlert> firestationAlerts = Arrays.asList(firestationAlert);
    int minorsNumber = 0, adultsNumber = 2;

    serialization.firestationAlertSerialization(firestationAlerts, method, argument, minorsNumber, adultsNumber);

    String fileName = serialization.generateFileName(method, argument);
    File file = new File(fileName);
    assertTrue(new File(fileName).exists());
    try {
      String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
      assertTrue(fileContent.contains("persons"));
      assertTrue(fileContent.contains("adults"));
      assertTrue(fileContent.contains("minors"));
      assertTrue(fileContent.contains("counters"));
      assertTrue(fileContent.contains(firestationAlert.getFirstName()));
      assertTrue(fileContent.contains(firestationAlert.getLastName()));
      assertTrue(fileContent.contains(firestationAlert.getAddress()));
      assertTrue(fileContent.contains(firestationAlert.getPhone()));

      file.delete();
      assertFalse(file.exists());
    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }

  @Test
  void testFloodSerialization() {
    FloodAlert flood_1 = new FloodAlert("Beraud", "06 00", 25, null, null);
    List<FloodAlert> floods_1 = Arrays.asList(flood_1);
    Household household = new Household(2, "Rue d'ici");
    FloodAlertByHousehold floodAlertByHousehold = new FloodAlertByHousehold(household, floods_1);
    List<FloodAlertByHousehold> floodAlertByHouseholds = Arrays.asList(floodAlertByHousehold);

    serialization.floodSerialization(floodAlertByHouseholds, method, argument);

    String fileName = serialization.generateFileName(method, argument);
    File file = new File(fileName);
    assertTrue(new File(fileName).exists());
    try {
      String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
      assertTrue(fileContent.contains("persons"));
      assertTrue(fileContent.contains(flood_1.getLastName()));
      assertTrue(fileContent.contains(flood_1.getPhone()));
      assertTrue(fileContent.contains(household.getAddress()));
      file.delete();
      assertFalse(file.exists());
    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }

  @Test
  void testPersonInfoSerialization() {
    PersonInfoAlert personInfo = new PersonInfoAlert("Quentin", "Beraud", "qbe@BE.fr", 60, null, null);
    List<PersonInfoAlert> personsInfos = Arrays.asList(personInfo);
    String firstName = "someFtName";
    String lastName = "someLtName";

    serialization.personInfoSerialization(personsInfos, method, firstName, lastName);

    String fileName = serialization.generateFileName(method, firstName + "_" + lastName);
    File file = new File(fileName);
    assertTrue(new File(fileName).exists());
    try {
      String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
      assertTrue(fileContent.contains("persons"));
      for (PersonInfoAlert personInfoX : personsInfos) {
        assertTrue(fileContent.contains(personInfoX.getLastName()));
        assertTrue(fileContent.contains(personInfoX.getMail()));
      }
      file.delete();
      assertFalse(file.exists());
    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }

  @Test
  void testPhoneAlertSerialization() {
    serialization.phoneAlertSerialization(persons, method, argument);
    String fileName = serialization.generateFileName(method, argument);
    File file = new File(fileName);

    assertTrue(new File(fileName).exists());
    try {
      String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
      assertTrue(fileContent.contains("phones"));
      assertTrue(fileContent.contains(person_1.getPhone()));
      file.delete();
      assertFalse(file.exists());
    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }
}