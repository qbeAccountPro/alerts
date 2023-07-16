package com.safetynet.alerts.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelWrapper {
    private List<Person> persons;
    private List<Firestation> firestations;
    private List<Medicalrecord> medicalrecords;
}
