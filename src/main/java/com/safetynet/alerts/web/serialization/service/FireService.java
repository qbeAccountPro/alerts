package com.safetynet.alerts.web.serialization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.service.BeanService;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.Fire;

@Service
public class FireService { //Erreur à vérifier
    BeanService beanService = new BeanService();

    public List<Fire> getFireList(List<Person> persons, List<MedicalRecord> medicalRecords) {
        List<Fire> fires = new ArrayList<>();
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                if (person.getFirstName().equals(medicalRecord.getFirstName())
                        && person.getLastName().equals(medicalRecord.getLastName())) {
                    Fire fire = new Fire();
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
