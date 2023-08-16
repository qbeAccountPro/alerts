package com.safetynet.alerts.web.serialization.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some javadoc.
 * Model class representing a child for the Child Alert feature.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildAlert {
    private String firstName;
    private String lastName;
    private int age;
}
