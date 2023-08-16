package com.safetynet.alerts.web.serialization.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some javadoc.
 * Model class representing a person affected by flood for the flood feature.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flood {
    private String lastName;
    private String phone;
    private String address;
    private int age;
    private List<String> medications;
    private List<String> allergies;
}
