package com.safetynet.alerts.web.deserialization;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deserialization {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<MedicalRecord> medicalrecords;
}
