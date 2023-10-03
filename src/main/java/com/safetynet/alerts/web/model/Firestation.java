package com.safetynet.alerts.web.model;

import java.util.List;

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
@AllArgsConstructor
@NoArgsConstructor
public class Firestation {
  private int id;
  private List<Integer> idHouseholds;
  private String station;
}