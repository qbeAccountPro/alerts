package com.safetynet.alerts.web.serialization.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfo { // TODO si plusieur habitant ils doivent tous apparaîtres.
    private String lastName;
    private String address;
    private String mail;
    private int age;
    private List<String> medications;
    private List<String> allergies;
}
