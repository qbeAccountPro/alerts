package com.safetynet.alerts.web.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some javadoc.
 * 
 * This class represents a Medical Record entity in the system.
 * It is used to link a medical information by person.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
  private int id;
  private int idPerson;
  private String birthdate;
  private List<String> medications;
  private List<String> allergies;
}