package com.safetynet.alerts.web.serialization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.Flood;
import com.safetynet.alerts.web.service.BeanService;

/**
 * Some javadoc.
 * Service class for generating a list of Flood objects from a list of Person
 * and MedicalRecord objects.
 */
@Service
public class FloodService {
    BeanService beanService = new BeanService();

    /**
     * Some javadoc.
     * Generate a list of Flood objects from a list of Person and MedicalRecord
     * objects.
     *
     * @param persons        The list of Person objects.
     * @param medicalRecords The list of MedicalRecord objects.
     * @return A list of Flood objects representing persons affected by flood from
     *         the input data.
     */
    public List<Flood> getFloodList(List<Person> persons, List<MedicalRecord> medicalRecords) {
        List<Flood> floods = new ArrayList<>();
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                if (person.getFirstName().equals(medicalRecord.getFirstName())
                        && person.getLastName().equals(medicalRecord.getLastName())) {
                    Flood flood = new Flood();
                    flood.setLastName(person.getLastName());
                    flood.setPhone(person.getPhone());
                    flood.setAge(beanService.convertBirthdateToAge(medicalRecord.getBirthdate()));
                    flood.setAddress(person.getAddress());
                    flood.setMedications(medicalRecord.getMedications());
                    flood.setAllergies(medicalRecord.getAllergies());
                    floods.add(flood);
                    break;
                }
            }
        }
        return floods;
    }
}
