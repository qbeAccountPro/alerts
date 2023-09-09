package com.safetynet.alerts.web.model;

import java.util.List;
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
 * This class represents a Medical Record entity in the system.
 * It is used to link a medical information by person.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int idPerson;
  private String birthdate;
  private List<String> medications;
  private List<String> allergies;
}