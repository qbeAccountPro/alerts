package com.safetynet.alerts.web.deserialization;

import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

import lombok.Data;

import java.util.List;

/**
 * Some javadoc.
 * This class represents a container for deserializing JSON data into Java
 * objects.
 * It is used to hold lists of Person, Firestation, and MedicalRecord objects
 * during deserialization.
 * The @Data annotation is from Lombok library to generate getters, setters, and
 * other boilerplate code.
 * 
 */
@Data
public class Deserialization {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalrecords;
}
