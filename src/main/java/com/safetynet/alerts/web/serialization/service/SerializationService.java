package com.safetynet.alerts.web.serialization.service;

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
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.dao.FirestationDao;

@Service
public class SerializationService {
    private ObjectMapper mapper;
    private SimpleModule module;

    public void firestationService(List<Person> persons, String method, String argument,
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
            mainObject.set("People covered by fire station number" + argument, personsCoveredArray);
            ObjectNode adultsAndMinorsObject = mapper.createObjectNode();
            adultsAndMinorsObject.put("Number of adults", adultsNumber);
            adultsAndMinorsObject.put("Number of minors", minorsNumber);
            mainObject.set("Count of adults and minors", adultsAndMinorsObject);
            mapper.writeValue(new File(setFileNameString(method, argument)), mainObject);
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public void serializationChildAlertQuery(List<MedicalRecord> medicalRecords, List<Person> persons, String method,
            String argument) {
                

    }

    public void serializationPhoneAlertQuery() {

    }

    public void serializationFireQuery() {

    }

    public void serializationStationsQuery() {

    }

    public void serializationPersonInfoQuery() {

    }

    public void serializationCommunityEmailQuery() {

    }

    public String setFileNameString(String method, String argument) {
        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedDate = now.format(formatter);
        String fileName = formattedDate + "_" + method + "_" + argument + ".json";
        return fileName;
    }

}
