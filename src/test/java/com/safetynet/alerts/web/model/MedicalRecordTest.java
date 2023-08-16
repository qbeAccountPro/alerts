package com.safetynet.alerts.web.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MedicalRecordTest {

    private MedicalRecord medicalRecord_1, medicalRecord_2;

    @BeforeEach
    public void setUp() {
        medicalRecord_1 = new MedicalRecord(0, "Quentin", "Beraud ", "12/31/1998", null, null);
        medicalRecord_2 = new MedicalRecord(0, "Quentin", "Beraud ", "12/31/1998", null, null);

    }

    /**
     * SomeJavadoc.
     * Test for the 'canEqual' method.
     */
    @Test
    void testCanEqual() {
        assertTrue(medicalRecord_1.canEqual(medicalRecord_2));
    }

    /**
     * SomeJavadoc.
     * Test for the 'equals' method.
     */
    @Test
    void testEquals() {
        assertTrue(medicalRecord_1.equals(medicalRecord_2));
    }

    /**
     * SomeJavadoc.
     * Test for the 'hashCode' method.
     */
    @Test
    void testHashCode() {
        assertEquals(medicalRecord_1.hashCode(), medicalRecord_2.hashCode());
    }
}
