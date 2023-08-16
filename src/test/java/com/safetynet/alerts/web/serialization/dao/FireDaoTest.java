package com.safetynet.alerts.web.serialization.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.web.serialization.model.Fire;

public class FireDaoTest {
    /**
     * SomeJavadoc.
     * Test 'Serialize' method for 'FireDao'.
     */
    @Test
    void testSerialize() {
        FireDao fireDao = new FireDao(Fire.class);
        Fire fire = new Fire();
        fire.setLastName("Beraud");
        fire.setPhone("06 66 66 66 66");
        fire.setAge(30);
        List<String> medications = new ArrayList<>();
        medications.add("Medication A");
        medications.add("Medication B");
        fire.setMedications(medications);
        List<String> allergies = new ArrayList<>();
        allergies.add("Allergy X");
        allergies.add("Allergy Y");
        fire.setAllergies(allergies);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            fireDao.serialize(fire, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "{\"lastName\":\"Beraud\",\"phone\":\"06 66 66 66 66\",\"age\":30," +
                    "\"medications\":[\"Medication A\",\"Medication B\"]," +
                    "\"allergies\":[\"Allergy X\",\"Allergy Y\"]}";
            String actualJson = writer.toString();

            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SomeJavadoc.
     * Test 'SerializeList' method for 'FireDao'.
     */
    @Test
    void testSerializeList() {
        FireDao fireDao = new FireDao(Fire.class);
        List<Fire> fireList = new ArrayList<>();
        Fire fire1 = new Fire();
        fire1.setLastName("Beraud");
        fire1.setPhone("06 66 66 66 66");
        fire1.setAge(30);
        List<String> medications1 = new ArrayList<>();
        medications1.add("Medication A");
        medications1.add("Medication B");
        fire1.setMedications(medications1);
        List<String> allergies1 = new ArrayList<>();
        allergies1.add("Allergy X");
        allergies1.add("Allergy Y");
        fire1.setAllergies(allergies1);

        Fire fire2 = new Fire();
        fire2.setLastName("Brish");
        fire2.setPhone("06 88 88 88 88");
        fire2.setAge(25);
        List<String> medications2 = new ArrayList<>();
        medications2.add("Medication C");
        fire2.setMedications(medications2);
        List<String> allergies2 = new ArrayList<>();
        allergies2.add("Allergy Z");
        fire2.setAllergies(allergies2);

        fireList.add(fire1);
        fireList.add(fire2);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            fireDao.serializeList(fireList, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "[{\"lastName\":\"Beraud\",\"phone\":\"06 66 66 66 66\",\"age\":30," +
                    "\"medications\":[\"Medication A\",\"Medication B\"]," +
                    "\"allergies\":[\"Allergy X\",\"Allergy Y\"]}," +
                    "{\"lastName\":\"Brish\",\"phone\":\"06 88 88 88 88\",\"age\":25," +
                    "\"medications\":[\"Medication C\"],\"allergies\":[\"Allergy Z\"]}]";
            String actualJson = writer.toString();

            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
