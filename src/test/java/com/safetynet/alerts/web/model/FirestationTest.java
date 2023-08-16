package com.safetynet.alerts.web.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FirestationTest {

  private Firestation firestation_1, firestation_2;

  @BeforeEach
  public void setUp() {
    firestation_1 = new Firestation(1, "Rue d'ici", "2");
    firestation_2 = new Firestation(1, "Rue d'ici", "2");
  }

  /**
   * SomeJavadoc.
   * Test for the 'canEqual' method.
   */
  @Test
  void testCanEqual() {
    assertTrue(firestation_1.canEqual(firestation_2));
  }

  /**
   * SomeJavadoc.
   * Test for the 'equals' method.
   */
  @Test
  void testEquals() {
    assertTrue(firestation_1.equals(firestation_2));
  }

  /**
   * SomeJavadoc.
   * Test for the 'hashCode' method.
   */
  @Test
  void testHashCode() {
    assertEquals(firestation_1.hashCode(), firestation_2.hashCode());
  }
}
