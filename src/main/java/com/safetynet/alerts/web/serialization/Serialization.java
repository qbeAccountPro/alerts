package com.safetynet.alerts.web.serialization;

import java.io.File;
import java.io.IOException;
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
import com.safetynet.alerts.web.serialization.dao.*;
import com.safetynet.alerts.web.serialization.model.*;

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
  public ResponseEntity<String> firestationAlertSerialization(List<FirestationAlert> firestationsAlert,
      String methodName,
      String argument,
      int minorsNumber, int adultsNumber) {
    FirestationAlertDao firestationDao = new FirestationAlertDao(FirestationAlert.class);
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
      mapper.writeValue(new File(generateFileName(methodName, argument)), mainObject);
      return log.successfullyGenerated(methodName);
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
  public ResponseEntity<String> childAlertSerialization(List<ChildAlert> children, List<ChildAlert> adults,
      String method,
      String argument) {
    ChildAlertDao childAlertDao = new ChildAlertDao(ChildAlert.class);
    OtherResidentsDao otherResidentsDao = new OtherResidentsDao(Person.class);
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
      mapper.writeValue(new File(generateFileName(method, argument)), childAlertObject);
      return log.successfullyGenerated(method);
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
  public ResponseEntity<String> phoneAlertSerialization(List<Person> persons, String method,
      String argument) {
    PhoneAlertDao phoneAlertDao = new PhoneAlertDao(Person.class);
    mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    module = new SimpleModule();
    module.addSerializer(Person.class, phoneAlertDao);
    mapper.registerModule(module);
    try {
      ObjectNode phoneAlertObject = mapper.createObjectNode();
      ArrayNode phoneAlertArray = mapper.valueToTree(persons);
      phoneAlertObject.set("phones", phoneAlertArray);
      mapper.writeValue(new File(generateFileName(method, argument)), phoneAlertObject);
      return log.successfullyGenerated(method);
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
  public ResponseEntity<String> fireSerialization(List<FireAlert> firesAlert, String firestationNumber, String method,
      String argument) {
    FireAlertDao fireDao = new FireAlertDao(FireAlert.class);
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
      mapper.writeValue(new File(generateFileName(method, argument)), fireObject);
      return log.successfullyGenerated(method);
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
  public ResponseEntity<String> floodSerialization(List<FloodAlertByHousehold> floodsAlertByHousehold, String method,
      String argument) {
    FloodAlertDao floodDao = new FloodAlertDao(FloodAlertByHousehold.class);
    mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    module = new SimpleModule();
    module.addSerializer(FloodAlertByHousehold.class, floodDao);
    mapper.registerModule(module);
    try {
      ObjectNode floodObject = mapper.createObjectNode();
      ArrayNode floodArray = mapper.valueToTree(floodsAlertByHousehold);
      floodObject.set("persons", floodArray);
      mapper.writeValue(new File(generateFileName(method, argument)), floodObject);
      return log.successfullyGenerated(method);
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
  public ResponseEntity<String> personInfoSerialization(List<PersonInfoAlert> personInfoAlert, String method,
      String firstName,
      String lastName) {
    PersonInfoDao personeInfoDao = new PersonInfoDao(PersonInfoAlert.class);
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
      mapper.writeValue(new File(generateFileName(method, firstName + "_" + lastName)), personInfoObject);
      return log.successfullyGenerated(method);
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
  public ResponseEntity<String> communityEmailSerialization(List<Person> persons, String method, String city) {
    CommunityEmailDao communityEmailDao = new CommunityEmailDao(Person.class);
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
      mapper.writeValue(new File(generateFileName(method, city)), emailObject);
      return log.successfullyGenerated(method);
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
  public ResponseEntity<String> emptyAnswer(String method, String argument) {
    File file = new File(generateFileName(method, argument));
    try {
      if (!file.exists()) {
        file.createNewFile();
      }
      return log.successfullyGenerated(method);
    } catch (IOException e) {
      e.printStackTrace();
      return log.threwAnException(method);
    }
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