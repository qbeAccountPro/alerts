package com.safetynet.alerts.web.serialization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.ChildAlert;
import com.safetynet.alerts.web.service.BeanService;

/**
 * Some javadoc.
 * 
 * Service class for generating a list of ChildAlert objects from a list of
 * Person and MedicalRecord objects.
 */
@Service
public class ChildAlertService {
    private BeanService beanService = new BeanService();

    /**
     * Some javadoc.
     * 
     * Generate a list of ChildAlert objects from a list of Person and MedicalRecord
     * objects.
     *
     * @param persons        The list of Person objects.
     * @param medicalRecords The list of MedicalRecord objects.
     * @return A list of ChildAlert objects representing children found in the input
     *         data.
     */
    public List<ChildAlert> getChildAlertListFromPersonList(List<Person> persons, List<MedicalRecord> medicalRecords) {
        List<ChildAlert> childrenAlert = new ArrayList<>();
        for (MedicalRecord medicalRecord : medicalRecords) {
            for (Person person : persons) {
                if (medicalRecord.getIdPerson() == person.getId()) {
                    ChildAlert childAlert = new ChildAlert();
                    childAlert.setFirstName(person.getFirstName());
                    childAlert.setLastName(person.getLastName());
                    childAlert.setAge(beanService.convertBirthdateToAge(medicalRecord.getBirthdate()));
                    childrenAlert.add(childAlert);
                    break;
                }
            }
        }
        return childrenAlert;
    }
}