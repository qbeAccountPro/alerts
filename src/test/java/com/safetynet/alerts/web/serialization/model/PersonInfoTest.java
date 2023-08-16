package com.safetynet.alerts.web.serialization.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class PersonInfoTest {

    private List<String> medications_1, medications_2, allergies_1, allergies_2;
    private PersonInfo personInfo_1, personInfo_2;

    @BeforeEach
    public void setUp() {
        medications_1 = Arrays.asList("Med1", "Med2");
        allergies_1 = Arrays.asList("Allergy1", "Allergy2");
        medications_2 = Arrays.asList("Med1", "Med2");
        allergies_2 = Arrays.asList("Allergy1", "Allergy2");
        personInfo_1 = new PersonInfo("Beraud", "69007  Rue du petit", "qbeqbeqbe@qbe.com", 30,
                medications_1,
                allergies_1);

        personInfo_2 = new PersonInfo("Beraud", "69007  Rue du petit", "qbeqbeqbe@qbe.com", 30,
                medications_2,
                allergies_2);
    }

    @Test
    void testCanEqual() {
        assertTrue(personInfo_1.canEqual(personInfo_2));
    }

    @Test
    void testEquals() {

        assertEquals(personInfo_1, personInfo_2);
    }

    @Test
    void testGetAddress() {
        assertEquals("69007  Rue du petit", personInfo_1.getAddress());
    }

    @Test
    void testGetAge() {
        assertEquals(30, personInfo_1.getAge());
    }

    @Test
    void testGetAllergies() {
        assertEquals(allergies_1, personInfo_1.getAllergies());
    }

    @Test
    void testGetLastName() {
        assertEquals("Beraud", personInfo_1.getLastName());
    }

    @Test
    void testGetMail() {
        assertEquals("qbeqbeqbe@qbe.com", personInfo_1.getMail());
    }

    @Test
    void testGetMedications() {
        assertEquals(medications_1, personInfo_1.getMedications());
    }

    @Test
    void testHashCode() {
        assertEquals(personInfo_1.hashCode(), personInfo_2.hashCode());
    }

    @Test
    void testSetAddress() {
        personInfo_1.setAddress("pas ici");
        assertEquals("pas ici", personInfo_1.getAddress());
    }

    @Test
    void testSetAge() {
        personInfo_1.setAge(99);
        assertEquals(99, personInfo_1.getAge());
    }

    @Test
    void testSetAllergies() {
        personInfo_1.setAllergies(null);
        assertEquals(null, personInfo_1.getAllergies());
    }

    @Test
    void testSetLastName() {
        personInfo_1.setLastName("Bernardo");
        assertEquals("Bernardo", personInfo_1.getLastName());
    }

    @Test
    void testSetMail() {
        personInfo_1.setMail("yahoo@o.com");
        assertEquals("yahoo@o.com", personInfo_1.getMail());
    }

    @Test
    void testSetMedications() {
        personInfo_1.setMedications(null);
        assertEquals(null, personInfo_1.getMedications());
    }

    @Test
    void testToString() {
        String expectedToString = "PersonInfo(lastName=Beraud, address=69007  Rue du petit, mail=qbeqbeqbe@qbe.com, age=30, medications=[Med1, Med2], allergies=[Allergy1, Allergy2])";
        assertEquals(expectedToString, personInfo_1.toString());
    }
}
