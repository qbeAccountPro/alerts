package com.safetynet.alerts.web.serialization.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class FireTest {
    private Fire fire_1, fire_2;
    private List<String> medications_1, medications_2, allergies_1, allergies_2;

    @BeforeEach
    public void setUp() {
        medications_1 = Arrays.asList("Med1", "Med2");
        allergies_1 = Arrays.asList("Allergy1", "Allergy2");
        fire_1 = new Fire("Beraud", "06 66 66 86 86", 30, medications_1, allergies_1);
        medications_2 = Arrays.asList("Med1", "Med2");
        allergies_2 = Arrays.asList("Allergy1", "Allergy2");
        fire_2 = new Fire("Beraud", "06 66 66 86 86", 30, medications_2, allergies_2);

    }

    @Test
    void testCanEqual() {
        assertTrue(fire_1.canEqual(fire_2));
    }

    @Test
    void testEquals() {
        assertEquals(fire_1, fire_2);
    }

    @Test
    void testGetAge() {
        assertEquals(30, fire_1.getAge());
    }

    @Test
    void testGetAllergies() {
        assertEquals(allergies_1, fire_1.getAllergies());
    }

    @Test
    void testGetLastName() {
        assertEquals("Beraud", fire_1.getLastName());
    }

    @Test
    void testGetMedications() {
        assertEquals(medications_1, fire_1.getMedications());
    }

    @Test
    void testGetPhone() {
        assertEquals("06 66 66 86 86", fire_1.getPhone());
    }

    @Test
    void testHashCode() {
        assertEquals(fire_1.hashCode(), fire_2.hashCode());
    }

    @Test
    void testSetAge() {
        fire_1.setAge(10000);
        assertEquals(10000, fire_1.getAge());
    }

    @Test
    void testSetAllergies() {
        fire_1.setAllergies(null);
        assertEquals(null, fire_1.getAllergies());
    }

    @Test
    void testSetLastName() {
        fire_1.setLastName("goup");
        assertEquals("goup", fire_1.getLastName());
    }

    @Test
    void testSetMedications() {
        fire_1.setMedications(null);
        assertEquals(null, fire_1.getMedications());
    }

    @Test
    void testSetPhone() {
        fire_1.setPhone("00");
        assertEquals("00", fire_1.getPhone());
    }

    @Test
    void testToString() {
        /*
         * List<String> medications = Arrays.asList("Med1", "Med2");
         * List<String> allergies = Arrays.asList("Allergy1", "Allergy2");
         * Fire fire = new Fire("Beraud", "06 66 66 86 86", 30, medications, allergies);
         */
        String expectedToString = "Fire(lastName=Beraud, phone=06 66 66 86 86, age=30, medications=[Med1, Med2], allergies=[Allergy1, Allergy2])";
        assertEquals(expectedToString, fire_1.toString());
    }
}
