package com.safetynet.alerts.web.serialization.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some javadoc.
 * 
 * Model class representing a FireAlert for the Fire feature.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FireAlert {
  private String lastName;
  private String phone;
  private int age;
  private List<String> medications;
  private List<String> allergies;
}