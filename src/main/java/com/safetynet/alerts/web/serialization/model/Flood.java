package com.safetynet.alerts.web.serialization.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flood { // TODO regrouper les personnes par adresse.
    private String lastName;
    private String phone;
    private String address;
    private int age;
    private List<String> medications;
    private List<String> allergies;
}
