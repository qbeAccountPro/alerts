package com.safetynet.alerts.web.serialization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.FirestationAlert;

/**
 * Some javadoc.
 * 
 * Service class for generating a list of PersonCovered objects from a list of
 * Person and Household objects.
 */
@Service
public class PersonCoveredService {

    public List<FirestationAlert> getPersonCoveredList(List<Person> persons, List<Household> households) {
        List<FirestationAlert> personsCovered = new ArrayList<>();
        for (Person person : persons) {
            for (Household household : households) {
                if (person.getIdHousehold() == household.getId()) {
                    FirestationAlert personCovered = new FirestationAlert();
                    personCovered.setFirstName(person.getFirstName());
                    personCovered.setLastName(person.getLastName());
                    personCovered.setPhone(person.getPhone());
                    personCovered.setAddress(household.getAddress());
                    personsCovered.add(personCovered);
                }
            }
        }
        return personsCovered;
    }
}