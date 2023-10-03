package com.safetynet.alerts.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some javadoc.
 * 
 * This class represents a Households entity in the system.
 * It is used to link a Person with a Firestation by address.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Household {
  private int id;
  private String address;
}