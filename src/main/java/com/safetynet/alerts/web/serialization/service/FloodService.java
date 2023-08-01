package com.safetynet.alerts.web.serialization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.Flood;
import com.safetynet.alerts.web.service.BeanService;

@Service
public class FloodService {
    BeanService beanService = new BeanService();

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
