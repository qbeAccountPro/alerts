package com.safetynet.alerts.web.serialization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.ChildAlert;
import com.safetynet.alerts.web.service.BeanService;

@Service
public class ChildAlertService {
    BeanService beanService = new BeanService();

    public List<ChildAlert> getChildAlertListFromPersonList(List<Person> persons, List<MedicalRecord> medicalRecords) {
        List<ChildAlert> childrenAlert = new ArrayList<>();
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                if (person.getFirstName().equals(medicalRecord.getFirstName())
                        && person.getLastName().equals(medicalRecord.getLastName())) {
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