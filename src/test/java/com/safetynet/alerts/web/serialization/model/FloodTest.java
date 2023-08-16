package com.safetynet.alerts.web.serialization.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

public class FloodTest {
    private Flood flood_1, flood_2;

    @BeforeEach
    public void setUp() {
        flood_1 = new Flood("Quentin", "06 67 79 82 52", "ici ou là", 30,
                Arrays.asList("Med1", "Med2"), Arrays.asList("Allergy1", "Allergy2"));
        flood_2 = new Flood("Quentin", "06 67 79 82 52", "ici ou là", 30,
                Arrays.asList("Med1", "Med2"), Arrays.asList("Allergy1", "Allergy2"));
    }

    @Test
    void testCanEqual() {
        assertTrue(flood_1.canEqual(flood_2));
    }

    @Test
    void testEquals() {
        assertEquals(flood_1, flood_2);
    }

    @Test
    void testGetAddress() {
        assertEquals("ici ou là", flood_1.getAddress());
    }

    @Test
    void testGetAge() {
        assertEquals(30, flood_1.getAge());
    }

    @Test
    void testGetAllergies() {
        assertEquals(Arrays.asList("Allergy1", "Allergy2"), flood_1.getAllergies());
    }

    @Test
    void testGetLastName() {
        assertEquals("Quentin", flood_1.getLastName());
    }

    @Test
    void testGetMedications() {
        assertEquals(Arrays.asList("Med1", "Med2"), flood_1.getMedications());
    }

    @Test
    void testGetPhone() {
        assertEquals("06 67 79 82 52", flood_1.getPhone());
    }

    @Test
    void testHashCode() {
        assertEquals(flood_1.hashCode(), flood_2.hashCode());
    }

    @Test
    void testSetAddress() {
        flood_1.setAddress("Pas là");
        assertEquals("Pas là", flood_1.getAddress());
    }

    @Test
    void testSetAge() {
        flood_1.setAge(2);
        assertEquals(2, flood_1.getAge());
    }

    @Test
    void testSetAllergies() {
        flood_1.setAllergies(null);
        assertEquals(null, flood_1.getAllergies());
    }

    @Test
    void testSetLastName() {
        flood_1.setLastName("bruce");
        assertEquals("bruce", flood_1.getLastName());
    }

    @Test
    void testSetMedications() {
        flood_1.setMedications(null);
        assertEquals(null, flood_1.getMedications());
    }

    @Test
    void testSetPhone() {
        flood_1.setPhone("08");
        assertEquals("08", flood_1.getPhone());
    }

    @Test
    void testToString() {
        String expectedToString = "Flood(lastName=Quentin, phone=06 67 79 82 52, address=ici ou là, age=30, medications=[Med1, Med2], allergies=[Allergy1, Allergy2])";
        assertEquals(expectedToString, flood_1.toString());
    }
}
