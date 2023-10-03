package com.safetynet.alerts.web.serialization;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.ChildAlert;
import com.safetynet.alerts.web.serialization.model.FireAlert;
import com.safetynet.alerts.web.serialization.model.FirestationAlert;
import com.safetynet.alerts.web.serialization.model.FloodAlert;
import com.safetynet.alerts.web.serialization.model.FloodAlertByHousehold;
import com.safetynet.alerts.web.serialization.model.PersonInfoAlert;
import com.safetynet.alerts.web.serialization.serializer.ChildAlertSerializer;
import com.safetynet.alerts.web.serialization.serializer.CommunityEmailSerializer;
import com.safetynet.alerts.web.serialization.serializer.FireAlertSerializer;
import com.safetynet.alerts.web.serialization.serializer.OtherResidentsSerializer;
import com.safetynet.alerts.web.serialization.serializer.PersonInfoSerializer;
import com.safetynet.alerts.web.serialization.serializer.PhoneAlertSerializer;

@ExtendWith(MockitoExtension.class)
public class SerializationTest {

  @Mock
  private ChildAlertSerializer childAlertDao;
  @Mock
  private CommunityEmailSerializer communityEmailDao;
  @Mock
  private FireAlertSerializer FireAlertDao;

  @Mock
  private OtherResidentsSerializer otherResidentsDao;
  @Mock
  private PersonInfoSerializer personeInfoDao;
  @Mock
  private PhoneAlertSerializer phoneAlertDao;

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

    ResponseEntity<ObjectNode> result = serialization.childAlertSerialization(children, adults, method, argument);
    ObjectNode jsonResponse = result.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    try {
      String jsonString = objectWriter.writeValueAsString(jsonResponse);

      assertTrue(jsonString.contains("children"));
      assertTrue(jsonString.contains(person_1.getFirstName()));
      assertTrue(jsonString.contains(person_2.getLastName()));
      assertTrue(jsonString.contains("adults"));
    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }

  @Test
  void testCommunityEmailSerialization() {
    ResponseEntity<ObjectNode> result = serialization.communityEmailSerialization(persons, method, argument);
    ObjectNode jsonResponse = result.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    try {
      String jsonString = objectWriter.writeValueAsString(jsonResponse);

      assertTrue(jsonString.contains("emails"));
      assertTrue(jsonString.contains(person_1.getEmail()));
      assertTrue(jsonString.contains(person_2.getEmail()));

    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }

  @Test
  void testEmptyAnswer() {
    ResponseEntity<ObjectNode> result = serialization.emptyAnswer(method, argument);

    ObjectNode jsonResponse = result.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    try {
      String jsonString = objectWriter.writeValueAsString(jsonResponse);

      assertTrue(jsonString.contains("null"));
    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }

  @Test
  void testFireSerialization() {
    FireAlert fire_1 = new FireAlert("Moti", "06 66 66 66 66", 35, null, null);
    List<FireAlert> fires = Arrays.asList(fire_1);
    String firestationNumber = "1";

    ResponseEntity<ObjectNode> result = serialization.fireSerialization(fires, firestationNumber, method, argument);
    ObjectNode jsonResponse = result.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    try {
      String jsonString = objectWriter.writeValueAsString(jsonResponse);
      assertTrue(jsonString.contains("persons"));
      assertTrue(jsonString.contains("station"));
      assertTrue(jsonString.contains("stationServing"));
      assertTrue(jsonString.contains("Moti"));
    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }

  @Test
  void testFirestationAlertSerialization() {
    FirestationAlert firestationAlert = new FirestationAlert("Quentin", "Beraud", "Rue d'ici", "000000");
    List<FirestationAlert> firestationAlerts = Arrays.asList(firestationAlert);
    int minorsNumber = 0, adultsNumber = 2;

    ResponseEntity<ObjectNode> result = serialization.firestationAlertSerialization(firestationAlerts, method, argument,
        minorsNumber, adultsNumber);

    ObjectNode jsonResponse = result.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    try {
      String jsonString = objectWriter.writeValueAsString(jsonResponse);

      assertTrue(jsonString.contains("persons"));
      assertTrue(jsonString.contains("adults"));
      assertTrue(jsonString.contains("minors"));
      assertTrue(jsonString.contains("counters"));
      assertTrue(jsonString.contains(firestationAlert.getFirstName()));
      assertTrue(jsonString.contains(firestationAlert.getLastName()));
      assertTrue(jsonString.contains(firestationAlert.getAddress()));
      assertTrue(jsonString.contains(firestationAlert.getPhone()));
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

    ResponseEntity<ObjectNode> result = serialization.floodSerialization(floodAlertByHouseholds, method, argument);

    ObjectNode jsonResponse = result.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    try {
      String jsonString = objectWriter.writeValueAsString(jsonResponse);

      assertTrue(jsonString.contains("persons"));
      assertTrue(jsonString.contains(flood_1.getLastName()));
      assertTrue(jsonString.contains(flood_1.getPhone()));
      assertTrue(jsonString.contains(household.getAddress()));
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

    ResponseEntity<ObjectNode> result = serialization.personInfoSerialization(personsInfos, method, firstName,
        lastName);

    ObjectNode jsonResponse = result.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    try {
      String jsonString = objectWriter.writeValueAsString(jsonResponse);

      assertTrue(jsonString.contains("persons"));
      for (PersonInfoAlert personInfoX : personsInfos) {
        assertTrue(jsonString.contains(personInfoX.getLastName()));
        assertTrue(jsonString.contains(personInfoX.getMail()));
      }
    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }

  @Test
  void testPhoneAlertSerialization() {
    ResponseEntity<ObjectNode> result = serialization.phoneAlertSerialization(persons, method, argument);
    ObjectNode jsonResponse = result.getBody();
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    try {
      String jsonString = objectWriter.writeValueAsString(jsonResponse);

      assertTrue(jsonString.contains("phones"));
      assertTrue(jsonString.contains(person_1.getPhone()));
    } catch (IOException e) {
      fail("Failed to read file: " + e.getMessage());
    }
  }
}