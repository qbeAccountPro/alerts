package com.safetynet.alerts.web.deserialization.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some javadoc.
 * 
 * This class represents a Medical Record entity from the JSON entity.
 * It is used to deserialize medical information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecordDeserialization {
    private int id;
    private String firstName;
    private String lastName;
    private String birthdate;
    private List<String> medications;
    private List<String> allergies;
}