package com.safetynet.alerts.web.serialization.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some javadoc.
 * 
 * Model class representing a person for the personInfo Alert feature.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfoAlert {
    private String lastName;
    private String address;
    private String mail;
    private int age;
    private List<String> medications;
    private List<String> allergies;
}