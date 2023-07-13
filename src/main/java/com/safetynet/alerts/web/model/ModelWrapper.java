package com.safetynet.alerts.web.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ModelWrapper {
    private List<Persons> persons;
    private List<Firestations> firestations;
    private List<Medicalrecords> medicalrecords;
}
