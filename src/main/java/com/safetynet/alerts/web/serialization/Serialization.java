package com.safetynet.alerts.web.serialization;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.alerts.web.logging.EndpointsLogger;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.ChildAlert;
import com.safetynet.alerts.web.serialization.model.FireAlert;
import com.safetynet.alerts.web.serialization.model.FirestationAlert;
import com.safetynet.alerts.web.serialization.model.FloodAlertByHousehold;
import com.safetynet.alerts.web.serialization.model.PersonInfoAlert;
import com.safetynet.alerts.web.serialization.serializer.ChildAlertSerializer;
import com.safetynet.alerts.web.serialization.serializer.CommunityEmailSerializer;
import com.safetynet.alerts.web.serialization.serializer.FireAlertSerializer;
import com.safetynet.alerts.web.serialization.serializer.FirestationAlertSerializer;
import com.safetynet.alerts.web.serialization.serializer.FloodAlertSerializer;
import com.safetynet.alerts.web.serialization.serializer.OtherResidentsSerializer;
import com.safetynet.alerts.web.serialization.serializer.PersonInfoSerializer;
import com.safetynet.alerts.web.serialization.serializer.PhoneAlertSerializer;

/**
 * Some javadoc.
 * 
 * Service class for JSON serialization of various alert features.
 */
@Service
public class Serialization {
  private ObjectMapper mapper;
  private SimpleModule module;
  private EndpointsLogger log = new EndpointsLogger();

  /**
   * Some javadoc.
   * 
   * Serialize and save data related to FirestationAlert objects.
   *
   * @param firestationsAlert List of FirestationAlert objects.
   * @param methodName        The method name.
   * @param argument          The argument value.
   * @param minorsNumber      The number of minors.
   * @param adultsNumber      The number of adults.
   */
  public ResponseEntity<ObjectNode> firestationAlertSerialization(List<FirestationAlert> firestationsAlert,
      String methodName,
      String argument,
      int minorsNumber, int adultsNumber) {
    FirestationAlertSerializer firestationDao = new FirestationAlertSerializer(FirestationAlert.class);
    mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    module = new SimpleModule();
    module.addSerializer(FirestationAlert.class, firestationDao);
    mapper.registerModule(module);

    try {
      ObjectNode mainObject = mapper.createObjectNode();
      ArrayNode personsCoveredArray = mapper.valueToTree(firestationsAlert);
      mainObject.set("persons", personsCoveredArray);
      ObjectNode adultsAndMinorsObject = mapper.createObjectNode();
      adultsAndMinorsObject.put("adults", adultsNumber);
      adultsAndMinorsObject.put("minors", minorsNumber);
      mainObject.set("counters", adultsAndMinorsObject);

      return log.successfullyGenerated(methodName, mainObject);
    } catch (Exception e) {
      System.out.println(e);
      return log.threwAnException(methodName);
    }
  }

  /**
   * Some javadoc.
   * 
   * Serialize and save data related to child alerts.
   *
   * @param children List of children .
   * @param adults   List of adults.
   * @param method   The method name.
   * @param argument The argument value.
   */
  public ResponseEntity<ObjectNode> childAlertSerialization(List<ChildAlert> children, List<ChildAlert> adults,
      String method,
      String argument) {
    ChildAlertSerializer childAlertDao = new ChildAlertSerializer(ChildAlert.class);
    OtherResidentsSerializer otherResidentsDao = new OtherResidentsSerializer(Person.class);
    SimpleModule childModule = new SimpleModule(), personModule = new SimpleModule();
    childModule.addSerializer(ChildAlert.class, childAlertDao);
    personModule.addSerializer(Person.class, otherResidentsDao);
    mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    mapper.registerModule(childModule);
    mapper.registerModule(personModule);

    try {
      ObjectNode childAlertObject = mapper.createObjectNode();
      ArrayNode childAlertArray = mapper.valueToTree(children);
      childAlertObject.set("children", childAlertArray);
      ArrayNode residentsArray = mapper.valueToTree(adults);
      childAlertObject.set("adults", residentsArray);
      return log.successfullyGenerated(method, childAlertObject);
    } catch (Exception e) {
      System.out.println(e);
      return log.threwAnException(method);
    }
  }

  /**
   * Some javadoc.
   * 
   * Serialize and save data related to phone alerts.
   *
   * @param persons  List of Person objects.
   * @param method   The method name.
   * @param argument The argument value.
   */
  public ResponseEntity<ObjectNode> phoneAlertSerialization(List<Person> persons, String method,
      String argument) {
    PhoneAlertSerializer phoneAlertDao = new PhoneAlertSerializer(Person.class);
    mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    module = new SimpleModule();
    module.addSerializer(Person.class, phoneAlertDao);
    mapper.registerModule(module);
    try {
      ObjectNode phoneAlertObject = mapper.createObjectNode();
      ArrayNode phoneAlertArray = mapper.valueToTree(persons);
      phoneAlertObject.set("phones", phoneAlertArray);
      return log.successfullyGenerated(method, phoneAlertObject);
    } catch (Exception e) {
      System.out.println(e);
      return log.threwAnException(method);
    }
  }

  /**
   * Some javadoc.
   * 
   * Serialize and save data related to fire alerts.
   *
   * @param firesAlert        List of FireAlert objects.
   * @param firestationNumber The fire station number.
   * @param method            The method name.
   * @param argument          The argument value.
   */
  public ResponseEntity<ObjectNode> fireSerialization(List<FireAlert> firesAlert, String firestationNumber,
      String method,
      String argument) {
    FireAlertSerializer fireDao = new FireAlertSerializer(FireAlert.class);
    mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    module = new SimpleModule();
    module.addSerializer(FireAlert.class, fireDao);
    mapper.registerModule(module);
    try {
      ObjectNode fireObject = mapper.createObjectNode();
      ArrayNode fireArray = mapper.valueToTree(firesAlert);
      fireObject.set("persons", fireArray);
      ObjectNode fireStationNumberObject = mapper.createObjectNode();
      fireStationNumberObject.put("station", firestationNumber);
      fireObject.set("stationServing", fireStationNumberObject);
      return log.successfullyGenerated(method, fireObject);
    } catch (Exception e) {
      System.out.println(e);
      return log.threwAnException(method);
    }
  }

  /**
   * Some javadoc.
   * 
   * Serialize and save data related to flood alerts.
   *
   * @param floodsAlertByHousehold List of floods objects by households.
   * @param method                 The method name.
   * @param argument               The argument value.
   */
  public ResponseEntity<ObjectNode> floodSerialization(List<FloodAlertByHousehold> floodsAlertByHousehold,
      String method,
      String argument) {
    FloodAlertSerializer floodDao = new FloodAlertSerializer(FloodAlertByHousehold.class);
    mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    module = new SimpleModule();
    module.addSerializer(FloodAlertByHousehold.class, floodDao);
    mapper.registerModule(module);
    try {
      ObjectNode floodObject = mapper.createObjectNode();
      ArrayNode floodArray = mapper.valueToTree(floodsAlertByHousehold);
      floodObject.set("persons", floodArray);
      return log.successfullyGenerated(method, floodObject);
    } catch (Exception e) {
      System.out.println(e);
      return log.threwAnException(method);
    }
  }

  /**
   * Some javadoc.
   * 
   * Serialize and save data related to person information.
   *
   * @param personInfoAlert List of personInfoAlert objects.
   * @param method          The method name.
   * @param firstName       The first name.
   * @param lastName        The last name.
   */
  public ResponseEntity<ObjectNode> personInfoSerialization(List<PersonInfoAlert> personInfoAlert, String method,
      String firstName,
      String lastName) {
    PersonInfoSerializer personeInfoDao = new PersonInfoSerializer(PersonInfoAlert.class);
    mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    module = new SimpleModule();
    module.addSerializer(PersonInfoAlert.class, personeInfoDao);
    mapper.registerModule(module);
    try {
      ObjectNode personInfoObject = mapper.createObjectNode();
      ArrayNode personInfoArray = mapper.valueToTree(personInfoAlert);
      personInfoObject.set("persons",
          personInfoArray);
      return log.successfullyGenerated(method, personInfoObject);
    } catch (Exception e) {
      System.out.println(e);
      return log.threwAnException(method);
    }
  }

  /**
   * Some javadoc.
   * 
   * Serialize and save data related to community emails.
   *
   * @param persons List of Person objects.
   * @param method  The method name.
   * @param city    The city name.
   */
  public ResponseEntity<ObjectNode> communityEmailSerialization(List<Person> persons, String method, String city) {
    CommunityEmailSerializer communityEmailDao = new CommunityEmailSerializer(Person.class);
    mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    module = new SimpleModule();
    module.addSerializer(Person.class, communityEmailDao);
    mapper.registerModule(module);
    try {
      ObjectNode emailObject = mapper.createObjectNode();
      ArrayNode emailArray = mapper.valueToTree(persons);
      emailObject.set("emails",
          emailArray);
      return log.successfullyGenerated(method, emailObject);
    } catch (Exception e) {
      System.out.println(e);
      return log.threwAnException(method);
    }
  }

  /**
   * Some javadoc.
   * 
   * Save an empty answer for a specific request.
   *
   * @param method   The method name.
   * @param argument The argument value.
   */
  public ResponseEntity<ObjectNode> emptyAnswer(String method, String argument) {
      return log.successfullyGenerated(method, null);
  }

  /**
   * Some javadoc.
   * 
   * Generate a file name based on the method and argument.
   *
   * @param method   The method name.
   * @param argument The argument value.
   * @return The generated file name.
   */
  public String generateFileName(String method, String argument) {
    LocalDateTime now = LocalDateTime.now();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
    String formattedDate = now.format(formatter);
    String fileName = formattedDate + "_" + method + "_" + argument + ".json";
    return fileName;
  }
}