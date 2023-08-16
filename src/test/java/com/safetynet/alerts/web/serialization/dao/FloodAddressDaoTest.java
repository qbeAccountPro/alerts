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
import com.safetynet.alerts.web.serialization.model.Flood;
import com.safetynet.alerts.web.serialization.model.FloodAddress;

public class FloodAddressDaoTest {

    /**
     * SomeJavadoc.
     * Test 'Serialize' method for 'FloodAddressDao'.
     */
    @Test
    void testSerialize() {
        FloodAddressDao floodAddressDao = new FloodAddressDao(FloodAddress.class);
        Flood flood = new Flood("Beraud", "06 66 66 66 66", "ici", 13, null, null);
        List<Flood> floods = new ArrayList<>();
        floods.add(flood);
        FloodAddress floodAddress = new FloodAddress("ici", floods);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            floodAddressDao.serialize(floodAddress, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "[{\"lastName\":\"Beraud\",\"age\":13,\"phone\":\"06 66 66 66 66\",\"medications\":[],\"allergies\":[]}]";
            String actualJson = writer.toString();
            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * SomeJavadoc.
     * Test 'SerializeList' method for 'FloodAddressDao'.
     */
    @Test
    void testSerializeList() {
        FloodAddressDao floodAddressDao = new FloodAddressDao(FloodAddress.class);
        List<FloodAddress> floodAddresses = new ArrayList<>();

        Flood flood_1 = new Flood("Beraud", "06 66 66 66 66", "ici", 13, null, null);
        List<Flood> floods_1 = new ArrayList<>();
        floods_1.add(flood_1);
        FloodAddress floodAddress_1 = new FloodAddress("ici", floods_1);

        Flood flood_2 = new Flood("Dark", "06 88 88 88 88", "pas ici", 26, null, null);
        List<Flood> floods_2 = new ArrayList<>();
        floods_2.add(flood_2);
        FloodAddress floodAddress_2 = new FloodAddress("ici", floods_2);

        floodAddresses.add(floodAddress_1);
        floodAddresses.add(floodAddress_2);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            StringWriter writer = new StringWriter();
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer);

            floodAddressDao.serializeList(floodAddresses, jsonGenerator, objectMapper.getSerializerProvider());
            jsonGenerator.flush();

            String expectedJson = "[[{\"lastName\":\"Beraud\",\"age\":13,\"phone\":\"06 66 66 66 66\",\"medications\":[],\"allergies\":[]}]] [[{\"lastName\":\"Dark\",\"age\":26,\"phone\":\"06 88 88 88 88\",\"medications\":[],\"allergies\":[]}]]";
            String actualJson = writer.toString();
            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
