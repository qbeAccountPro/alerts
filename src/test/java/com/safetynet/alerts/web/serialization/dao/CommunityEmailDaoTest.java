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

public class CommunityEmailDaoTest {

    /**
     * SomeJavadoc.
     * Test 'Serialize' method for 'CommunityEmailDao'.
     */
    @Test
    void testSerialize() {
        CommunityEmailDao communityEmailDao = new CommunityEmailDao(Person.class);
        Person person = new Person();
        person.setEmail("qbe@yahoo.com");

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            communityEmailDao.serialize(person, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "{\"email\":\"qbe@yahoo.com\"}";
            String actualJson = writer.toString();

            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SomeJavadoc.
     * Test 'SerializeList' method for 'ChildAlertDao'.
     */
    @Test
    void testSerializeList() {
        CommunityEmailDao communityEmailDao = new CommunityEmailDao(Person.class);
        List<Person> personList = new ArrayList<>();
        Person person1 = new Person();
        person1.setEmail("qbe@yahoo.com");
        Person person2 = new Person();
        person2.setEmail("jane@yahoo.com");
        personList.add(person1);
        personList.add(person2);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            communityEmailDao.serializeList(personList, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "[{\"email\":\"qbe@yahoo.com\"}," +
                    "{\"email\":\"jane@yahoo.com\"}]";
            String actualJson = writer.toString();

            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
