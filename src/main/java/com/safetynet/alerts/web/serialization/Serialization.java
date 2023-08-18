package com.safetynet.alerts.web.serialization;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.alerts.web.model.*;
import com.safetynet.alerts.web.serialization.dao.*;
import com.safetynet.alerts.web.serialization.model.*;

/**
 * Some javadoc.
 * Service class for JSON serialization of various data.
 */
@Service
public class Serialization {
    private ObjectMapper mapper;
    private SimpleModule module;

    /**
     * Some javadoc.
     * Serialize and save data related to fire stations.
     *
     * @param persons      List of Person objects.
     * @param method       The method name.
     * @param argument     The argument value.
     * @param minorsNumber The number of minors.
     * @param adultsNumber The number of adults.
     */
    public void firestationSerialization(List<Person> persons, String method, String argument,
            int minorsNumber, int adultsNumber) {
        FirestationDao firestationDao = new FirestationDao(Person.class);
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        module = new SimpleModule();
        module.addSerializer(Person.class, firestationDao);
        mapper.registerModule(module);
        try {
            ObjectNode mainObject = mapper.createObjectNode();
            ArrayNode personsCoveredArray = mapper.valueToTree(persons);
            mainObject.set("persons", personsCoveredArray);
            ObjectNode adultsAndMinorsObject = mapper.createObjectNode();
            adultsAndMinorsObject.put("adults", adultsNumber);
            adultsAndMinorsObject.put("minors", minorsNumber);
            mainObject.set("counters", adultsAndMinorsObject);
            mapper.writeValue(new File(setFileNameString(method, argument)), mainObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Some javadoc.
     * Serialize and save data related to child alerts.
     *
     * @param children List of children .
     * @param adults   List of adults.
     * @param method   The method name.
     * @param argument The argument value.
     */
    public void childAlertSerialization(List<ChildAlert> children, List<ChildAlert> adults, String method,
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
            mapper.writeValue(new File(setFileNameString(method, argument)), childAlertObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Some javadoc.
     * Serialize and save data related to phone alerts.
     *
     * @param persons  List of Person objects.
     * @param method   The method name.
     * @param argument The argument value.
     */
    public void phoneAlertSerialization(List<Person> persons, String method,
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
            mapper.writeValue(new File(setFileNameString(method, argument)), phoneAlertObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Some javadoc.
     * Serialize and save data related to fire alerts.
     *
     * @param fires             List of Fire objects.
     * @param firestationNumber The fire station number.
     * @param method            The method name.
     * @param argument          The argument value.
     */
    public void fireSerialization(List<Fire> fires, String firestationNumber, String method, String argument) {
        FireDao fireDao = new FireDao(Fire.class);
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        module = new SimpleModule();
        module.addSerializer(Fire.class, fireDao);
        mapper.registerModule(module);
        try {
            ObjectNode fireObject = mapper.createObjectNode();
            ArrayNode fireArray = mapper.valueToTree(fires);
            fireObject.set("persons", fireArray);
            ObjectNode fireStationNumberObject = mapper.createObjectNode();
            fireStationNumberObject.put("station", firestationNumber);
            fireObject.set("stationServing", fireStationNumberObject);
            mapper.writeValue(new File(setFileNameString(method, argument)), fireObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Some javadoc.
     * Serialize and save data related to flood alerts.
     *
     * @param floods   List of FloodAddress objects.
     * @param method   The method name.
     * @param argument The argument value.
     */
    public void floodSerialization(List<FloodAddress> floods, String method, String argument) {
        FloodAddressDao floodAddressDao = new FloodAddressDao(FloodAddress.class);
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        module = new SimpleModule();
        module.addSerializer(FloodAddress.class, floodAddressDao);
        mapper.registerModule(module);
        try {
            ObjectNode floodObject = mapper.createObjectNode();
            ArrayNode floodArray = mapper.valueToTree(floods);
            floodObject.set("persons", floodArray);
            mapper.writeValue(new File(setFileNameString(method, argument)), floodObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Some javadoc.
     * Serialize and save data related to person information.
     *
     * @param personsInfo List of PersonInfo objects.
     * @param method      The method name.
     * @param firstName   The first name.
     * @param lastName    The last name.
     */
    public void personInfoSerialization(List<PersonInfo> personsInfo, String method, String firstName,
            String lastName) {
        PersonInfoDao personeInfoDao = new PersonInfoDao(PersonInfo.class);
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        module = new SimpleModule();
        module.addSerializer(PersonInfo.class, personeInfoDao);
        mapper.registerModule(module);
        try {
            ObjectNode personInfoObject = mapper.createObjectNode();
            ArrayNode personInfoArray = mapper.valueToTree(personsInfo);
            personInfoObject.set("persons",
                    personInfoArray);
            mapper.writeValue(new File(setFileNameString(method, firstName + "_" + lastName)), personInfoObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Some javadoc.
     * Serialize and save data related to community emails.
     *
     * @param persons List of Person objects.
     * @param method  The method name.
     * @param city    The city name.
     */
    public void communityEmailSerialization(List<Person> persons, String method, String city) {
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
            mapper.writeValue(new File(setFileNameString(method, city)), emailObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Some javadoc.
     * Save an empty answer for a specific request.
     *
     * @param method   The method name.
     * @param argument The argument value.
     */
    public void emptyAnswer(String method, String argument) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(setFileNameString(method, argument)), "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Some javadoc.
     * Generate a file name based on the method and argument.
     *
     * @param method   The method name.
     * @param argument The argument value.
     * @return The generated file name.
     */
    public String setFileNameString(String method, String argument) {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedDate = now.format(formatter);
        String fileName = formattedDate + "_" + method + "_" + argument + ".json";
        return fileName;
    }

}
