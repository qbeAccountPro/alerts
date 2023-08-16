package com.safetynet.alerts.web.serialization.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class ChildAlertTest {
    private ChildAlert childAlert_1, childAlert_2;

    @BeforeEach
    public void setUp() {
        childAlert_1 = new ChildAlert("Quentin", "Beraud", 13);
        childAlert_2 = new ChildAlert("Quentin", "Beraud", 13);
    }

    @Test
    void testCanEqual() {
        assertTrue(childAlert_1.canEqual(childAlert_2));
    }

    @Test
    void testEquals() {
        assertEquals(childAlert_1, childAlert_2);
    }

    @Test
    void testGetAge() {
        assertEquals(13, childAlert_1.getAge());
    }

    @Test
    void testGetFirstName() {
        assertEquals("Quentin", childAlert_1.getFirstName());
    }

    @Test
    void testGetLastName() {
        assertEquals("Beraud", childAlert_1.getLastName());
    }

    @Test
    void testHashCode() {
        assertEquals(childAlert_1.hashCode(), childAlert_2.hashCode());
    }

    @Test
    void testSetAge() {
        childAlert_1.setAge(1000);
        assertEquals(1000, childAlert_1.getAge());
    }

    @Test
    void testSetFirstName() {
        childAlert_1.setFirstName("Locky");
        assertEquals("Locky", childAlert_1.getFirstName());
    }

    @Test
    void testSetLastName() {
        childAlert_1.setLastName("BNP");
        assertEquals("BNP", childAlert_1.getLastName());
    }

    @Test
    void testToString() {
        String expectedToString = "ChildAlert(firstName=Quentin, lastName=Beraud, age=13)";
        assertEquals(expectedToString, childAlert_1.toString());
    }
}
