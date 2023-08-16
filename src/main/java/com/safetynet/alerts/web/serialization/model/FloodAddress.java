package com.safetynet.alerts.web.serialization.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Some javadoc.
 * Model class representing a list of person by address.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FloodAddress {
    private String address;
    private List<Flood> flood;
}
