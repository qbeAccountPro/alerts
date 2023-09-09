package com.safetynet.alerts.web.deserialization;

import lombok.Data;

import java.util.List;

import com.safetynet.alerts.web.deserialization.model.FirestationDeserialization;
import com.safetynet.alerts.web.deserialization.model.MedicalRecordDeserialization;
import com.safetynet.alerts.web.deserialization.model.PersonDeserialization;

/**
 * Some javadoc.
 * 
 * This class represents a container for deserializing JSON data into Java
 * objects. It is used to hold lists of Person, Firestation, MedicalRecord
 * objects during deserialization.
 */
@Data
public class Deserialization {
  private List<PersonDeserialization> persons;
  private List<FirestationDeserialization> firestations;
  private List<MedicalRecordDeserialization> medicalrecords;
}
