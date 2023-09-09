package com.safetynet.alerts.web.deserialization.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FirestationDeserialization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String address;
    private String station;
}
