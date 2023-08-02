package com.safetynet.alerts.web.serialization;

import java.io.File;
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

@Service
public class Serialization {
    private ObjectMapper mapper;
    private SimpleModule module;

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
            mainObject.set("People covered by fire station number " + argument, personsCoveredArray);
            ObjectNode adultsAndMinorsObject = mapper.createObjectNode();
            adultsAndMinorsObject.put("Number of adults", adultsNumber);
            adultsAndMinorsObject.put("Number of minors", minorsNumber);
            mainObject.set("Count of adults and minors", adultsAndMinorsObject);
            mapper.writeValue(new File(setFileNameString(method, argument)), mainObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void childAlertSerialization(List<ChildAlert> children, List<Person> otherResidents, String method,
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
            childAlertObject.set("Children living at this address : " + argument, childAlertArray);
            ArrayNode residentsArray = mapper.valueToTree(otherResidents);
            childAlertObject.set("Other Residents living at this address : " + argument, residentsArray);
            mapper.writeValue(new File(setFileNameString(method, argument)), childAlertObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

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
            phoneAlertObject.set("List of phone covered by station number :" + argument, phoneAlertArray);
            mapper.writeValue(new File(setFileNameString(method, argument)), phoneAlertObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

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
            fireObject.set("List of persons covered by firestation number :" + firestationNumber
                    + ", from this address :" + argument, fireArray);
            mapper.writeValue(new File(setFileNameString(method, argument)), fireObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void floodSerialization(List<FloodAddress> floods, String method, String argument) {
        FloodAddressDao floodAddressDao = new FloodAddressDao(FloodAddress.class);
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        module = new SimpleModule();
        module.addSerializer(FloodAddress.class, floodAddressDao);
        mapper.registerModule(module);
        try {
            ObjectNode floodObject = mapper.createObjectNode();
            for (FloodAddress floodAddress : floods) {
                ArrayNode floodArray = mapper.valueToTree(floodAddress);
                floodObject.set("Persons covered by firestation number : " + argument + ". From the address : "
                        + floodAddress.getAddress(), floodArray);
                mapper.writeValue(new File(setFileNameString(method, argument)), floodObject);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void personInfoSerialization(List<PersonInfo> personsInfo, String method, String firstName,
            String lastName) {
        PersoneInfoDao personeInfoDao = new PersoneInfoDao(PersonInfo.class);
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        module = new SimpleModule();
        module.addSerializer(PersonInfo.class, personeInfoDao);
        mapper.registerModule(module);
        try {
            ObjectNode personInfoObject = mapper.createObjectNode();
            ArrayNode personInfoArray = mapper.valueToTree(personsInfo);
            personInfoObject.set("List of persons with first name:" + firstName + " and last name : " + lastName,
                    personInfoArray);
            mapper.writeValue(new File(setFileNameString(method, firstName + "_" + lastName)), personInfoObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

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
            emailObject.set("List of emails from each persons of city : " + city,
                    emailArray);
            mapper.writeValue(new File(setFileNameString(method, city)), emailObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public String setFileNameString(String method, String argument) {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedDate = now.format(formatter);
        String fileName = formattedDate + "_" + method + "_" + argument + ".json";
        return fileName;
    }

}
