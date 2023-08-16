package com.safetynet.alerts.web.serialization.dao;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.safetynet.alerts.web.serialization.model.PersonInfo;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PersonInfoDaoTest {

    /**
     * SomeJavadoc.
     * Test 'Serialize' method for 'PersonInfoDao'.
     */
    @Test
    void testSerialize() {
        PersonInfoDao personInfoDao = new PersonInfoDao(PersonInfo.class);

        PersonInfo personInfo = new PersonInfo("Beraud", "123 ici", "qbe@yahoo.com", 30, null, null);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            personInfoDao.serialize(personInfo, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "{\"lastName\":\"Beraud\",\"address\":\"123 ici\",\"age\":30,\"phone\":\"qbe@yahoo.com\",\"medications\":[],\"allergies\":[]}";
            String actualJson = writer.toString();

            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SomeJavadoc.
     * Test 'SerializeList' method for 'PersonInfoDao'.
     */
    @Test
    void testSerializeList() {
        PersonInfoDao personInfoDao = new PersonInfoDao(PersonInfo.class);

        List<PersonInfo> personsInfos = new ArrayList<>();

        PersonInfo personInfo_1 = new PersonInfo("Beraud", "123 ici", "qbe@yahoo.com", 30, null, null);
        PersonInfo personInfo_2 = new PersonInfo("Jacky", "123 pas là", "jacky@yahoo.com", 3, null, null);

        personsInfos.add(personInfo_1);
        personsInfos.add(personInfo_2);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            personInfoDao.serializeList(personsInfos, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "[{\"lastName\":\"Beraud\",\"address\":\"123 ici\",\"age\":30,\"phone\":\"qbe@yahoo.com\",\"medications\":[],\"allergies\":[]},"
                    +
                    "{\"lastName\":\"Jacky\",\"address\":\"123 pas l\u00E0\",\"age\":3,\"phone\":\"jacky@yahoo.com\",\"medications\":[],\"allergies\":[]}]";
            String actualJson = writer.toString();

            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
