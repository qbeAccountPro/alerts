package com.safetynet.alerts.web.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {

  private Person person_1, person_2;

  @BeforeEach
  public void setUp() {
    person_1 = new Person(1, "Quentin", "Beraud", "123 city", "Lyon", "69000", "06 66 66 66 66",
        "QBe@yahoo");
    person_2 = new Person(1, "Quentin", "Beraud", "123 city", "Lyon", "69000", "06 66 66 66 66",
        "QBe@yahoo");
  }

  /**
   * SomeJavadoc.
   * Test for the 'equals' method.
   */
  @Test
  void testEquals() {
    assertTrue(person_1.equals(person_2));
  }

  /**
   * SomeJavadoc.
   * Test for the 'hashCode' method.
   */
  @Test
  void testHashCode() {
    assertEquals(person_1.hashCode(), person_2.hashCode());
  }

  /**
   * SomeJavadoc.
   * Test for the 'canEqual' method.
   */
  @Test
  void testCanEqual() {
    assertTrue(person_1.canEqual(person_2));
  }
}
