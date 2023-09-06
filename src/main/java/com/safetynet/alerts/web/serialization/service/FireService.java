package com.safetynet.alerts.web.serialization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.service.BeanService;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.FireAlert;

/**
 * Some javadoc.
 * 
 * Service class for generating a list of Fire objects from a list of Person and
 * MedicalRecord objects.
 */
@Service
public class FireService {
    private BeanService beanService = new BeanService();

    /**
     * Some javadoc.
     * 
     * Generate a list of Fire objects from a list of Person and MedicalRecord
     * objects.
     *
     * @param persons        The list of Person objects.
     * @param medicalRecords The list of MedicalRecord objects.
     * @return A list of Fire objects representing persons affected by fire from the
     *         input data.
     */
    public List<FireAlert> getFireList(List<Person> persons, List<MedicalRecord> medicalRecords) {
        List<FireAlert> fires = new ArrayList<>();
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                if (person.getId() == medicalRecord.getIdPerson()) {
                    FireAlert fire = new FireAlert();
                    fire.setLastName(person.getLastName());
                    fire.setPhone(person.getPhone());
                    fire.setAge(beanService.convertBirthdateToAge(medicalRecord.getBirthdate()));
                    fire.setMedications(medicalRecord.getMedications());
                    fire.setAllergies(medicalRecord.getAllergies());
                    fires.add(fire);
                    break;
                }
            }
        }
        return fires;
    }
}