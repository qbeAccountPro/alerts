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
import com.safetynet.alerts.web.model.Person;

public class FirestationDaoTest {

    /**
     * SomeJavadoc.
     * Test 'SerializeList' method for 'FirestationDao'.
     */
    @Test
    void testSerialize() {
        FirestationDao firestationDao = new FirestationDao(Person.class);
        Person person = new Person();
        person.setFirstName("Quentin");
        person.setLastName("Beraud");
        person.setAddress("123 ici");
        person.setPhone("06 66 66 66 66");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            firestationDao.serialize(person, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "{\"firstName\":\"Quentin\",\"lastName\":\"Beraud\",\"address\":\"123 ici\",\"phone\":\"06 66 66 66 66\"}";
            String actualJson = writer.toString();

            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SomeJavadoc.
     * Test 'SerializeList' method for 'FirestationDao'.
     */
    @Test
    void testSerializeList() {
        FirestationDao firestationDao = new FirestationDao(Person.class);
        List<Person> personList = new ArrayList<>();
        Person person1 = new Person();
        person1.setFirstName("Quentin");
        person1.setLastName("Beraud");
        person1.setAddress("123 ici");
        person1.setPhone("06 66 66 66 66");

        Person person2 = new Person();
        person2.setFirstName("Jane");
        person2.setLastName("d'arc");
        person2.setAddress("456 par ici");
        person2.setPhone("06 88 88 88 88");

        personList.add(person1);
        personList.add(person2);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            firestationDao.serializeList(personList, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "[{\"firstName\":\"Quentin\",\"lastName\":\"Beraud\",\"address\":\"123 ici\",\"phone\":\"06 66 66 66 66\"},"
                    +
                    "{\"firstName\":\"Jane\",\"lastName\":\"d'arc\",\"address\":\"456 par ici\",\"phone\":\"06 88 88 88 88\"}]";
            String actualJson = writer.toString();

            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
