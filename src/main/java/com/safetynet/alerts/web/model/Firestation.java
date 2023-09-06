package com.safetynet.alerts.web.model;

import java.util.List;

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
 * This class represents a Firestation object in the system.
 * It is used to map Firestation data from the database.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Firestation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private List<Integer> idHouseholds;
    private String station;
}
