package com.safetynet.alerts.web.serialization.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class FloodAddressTest {
    private FloodAddress floodAddress_1, floodAddress_2;

    @BeforeEach
    public void setUp() {
        List<Flood> floodList1 = new ArrayList<>(Arrays.asList(new Flood(), new Flood()));
        floodAddress_1 = new FloodAddress("Par ici", floodList1);

        List<Flood> floodList2 = new ArrayList<>(Arrays.asList(new Flood(), new Flood()));
        floodAddress_2 = new FloodAddress("Par ici", floodList2);

    }

    @Test
    void testCanEqual() {
        assertTrue(floodAddress_1.canEqual(floodAddress_2));
    }

    @Test
    void testEquals() {
        assertEquals(floodAddress_1, floodAddress_2);
    }

    @Test
    void testGetAddress() {
        assertEquals("Par ici", floodAddress_1.getAddress());
    }

    @Test
    void testGetFlood() {
        List<Flood> floodList = new ArrayList<>(Arrays.asList(new Flood(), new Flood()));
        assertEquals(floodList, floodAddress_1.getFlood());
    }

    @Test
    void testHashCode() {
        assertEquals(floodAddress_1.hashCode(), floodAddress_2.hashCode());
    }

    @Test
    void testSetAddress() {
        floodAddress_1.setAddress("ici");
        assertEquals("ici", floodAddress_1.getAddress());
    }

    @Test
    void testSetFlood() {
        List<Flood> floodList = new ArrayList<>();
        floodAddress_1.setFlood(floodList);
        assertEquals(floodList, floodAddress_1.getFlood());
    }

    @Test
    void testToString() {
        String expectedToString = "FloodAddress(address=Par ici, flood=[Flood(lastName=null, phone=null, address=null, age=0, medications=null, allergies=null), Flood(lastName=null, phone=null, address=null, age=0, medications=null, allergies=null)])";
        assertEquals(expectedToString, floodAddress_1.toString());
    }
}
