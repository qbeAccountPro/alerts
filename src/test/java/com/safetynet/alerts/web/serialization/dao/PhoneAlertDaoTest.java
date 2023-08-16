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

public class PhoneAlertDaoTest {

    /**
     * SomeJavadoc.
     * Test 'Serialize' method for 'PhoneAlertDao'.
     */
    @Test
    void testSerialize() {
        PhoneAlertDao phoneAlertDao = new PhoneAlertDao(Person.class);

        Person person = new Person();
        person.setPhone("06 66 66 66 66");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            phoneAlertDao.serialize(person, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "{\"phone\":\"06 66 66 66 66\"}";
            String actualJson = writer.toString();

            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SomeJavadoc.
     * Test 'SerializeList' method for 'PhoneAlertDao'.
     */
    @Test
    void testSerializeList() {
        PhoneAlertDao phoneAlertDao = new PhoneAlertDao(Person.class);

        List<Person> persons = new ArrayList<>();
        Person person1 = new Person();
        person1.setPhone("06 66 66 66 66");

        Person person2 = new Person();
        person2.setPhone("06 88 88 88 88");

        persons.add(person1);
        persons.add(person2);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            phoneAlertDao.serializeList(persons, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "[{\"phone\":\"06 66 66 66 66\"},{\"phone\":\"06 88 88 88 88\"}]";
            String actualJson = writer.toString();

            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
