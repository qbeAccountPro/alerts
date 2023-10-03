package com.safetynet.alerts.web.deserialization.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some javadoc.
 * 
 * This class represents a Firestation entity from the JSON entity.
 * 
 * It is used to deserialize the firestation data.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirestationDeserialization {

    private int id;
    private String address;
    private String station;
}
