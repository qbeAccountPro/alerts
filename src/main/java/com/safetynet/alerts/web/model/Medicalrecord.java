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
 * This class represents a Medical Record entity in the system.
 * It is used to store medical information for individuals.
 * 
 * The @Data annotation is from Lombok library to generate getters, setters, and
 * other boilerplate code.
 * 
 * The @Entity annotation indicates that this class is a JPA entity and will be
 * mapped to a database table.
 * 
 * The @AllArgsConstructor and @NoArgsConstructor annotations are from Lombok to
 * generate constructors.
 * 
 * The @ToString annotation is from Lombok to generate a toString() method for
 * debugging purposes.
 */

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;
}
