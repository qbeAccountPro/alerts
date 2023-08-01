package com.safetynet.alerts.web.serialization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.service.BeanService;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.PersonInfo;

@Service
public class PersonInfoService {
    BeanService beanService = new BeanService();

    public List<PersonInfo> getPersonInfoList(List<Person> persons, List<MedicalRecord> medicalRecords) {
        List<PersonInfo> personsInfo = new ArrayList<>();
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                if (person.getFirstName().equals(medicalRecord.getFirstName())
                        && person.getLastName().equals(medicalRecord.getLastName())) {
                    PersonInfo personInfo = new PersonInfo();
                    personInfo.setLastName(person.getLastName());
                    personInfo.setAddress(person.getAddress());
                    personInfo.setAge(beanService.convertBirthdateToAge(medicalRecord.getBirthdate()));
                    personInfo.setMail(person.getEmail());
                    personInfo.setMedications(medicalRecord.getMedications());
                    personInfo.setAllergies(medicalRecord.getAllergies());
                    personsInfo.add(personInfo);
                    break;
                }
            }
        }
        return personsInfo;
    }
}
