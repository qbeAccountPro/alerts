package com.safetynet.alerts.web.deserialization.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some javadoc.
 * 
 * This class represents a Person entity entity from the JSON entity.
 * It is used to deserializestore information about individuals.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDeserialization {
    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;
}
