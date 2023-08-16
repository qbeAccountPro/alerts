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
import com.safetynet.alerts.web.serialization.model.ChildAlert;

public class ChildAlertDaoTest {

    /**
     * SomeJavadoc.
     * Test 'Serialize' method for 'ChildAlertDao'.
     */
    @Test
    void testSerialize() {
        ChildAlertDao childAlertDao = new ChildAlertDao(ChildAlert.class);
        ChildAlert child1 = new ChildAlert("Quentin", "Beraud", 5);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            childAlertDao.serialize(child1, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "{\"firstName\":\"Quentin\",\"lastName\":\"Beraud\",\"age\":5}";
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
        ChildAlertDao childAlertDao = new ChildAlertDao(ChildAlert.class);
        ChildAlert child1 = new ChildAlert("Quentin", "Beraud", 5);
        ChildAlert child2 = new ChildAlert("Jeanne", "d'arc", 8);
        List<ChildAlert> childrenAlertList = new ArrayList<>();
        childrenAlertList.add(child1);
        childrenAlertList.add(child2);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            childAlertDao.serializeList(childrenAlertList, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "[{\"firstName\":\"Quentin\",\"lastName\":\"Beraud\",\"age\":5}," +
                    "{\"firstName\":\"Jeanne\",\"lastName\":\"d'arc\",\"age\":8}]";
            String actualJson = writer.toString();

            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
