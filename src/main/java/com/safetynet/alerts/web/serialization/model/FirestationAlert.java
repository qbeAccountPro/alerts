package com.safetynet.alerts.web.serialization.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some javadoc.
 * 
 * Model class representing a FirestationAlert for the firestation alert
 * feature.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirestationAlert {
  private String firstName;
  private String lastName;
  private String address;
  private String phone;
}