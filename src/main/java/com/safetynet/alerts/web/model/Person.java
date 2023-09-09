package com.safetynet.alerts.web.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some javadoc.
 * 
 * This class represents a Person entity in the system.
 * It is used to store information about individuals and link housholds
 * corresponding.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Person {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int idHousehold;
  private String firstName;
  private String lastName;
  private String city;
  private String zip;
  private String phone;
  private String email;
}