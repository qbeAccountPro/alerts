package com.safetynet.alerts.web.serialization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.service.BeanService;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.PersonInfoAlert;

/**
 * Some javadoc.
 * 
 * Service class for generating a list of PersonInfo objects from a list of
 * Person and MedicalRecord objects.
 */
@Service
public class PersonInfoService {
    BeanService beanService = new BeanService();

    /**
     * Some javadoc.
     * 
     * Generate a list of PersonInfo objects from a list of Person and MedicalRecord
     * objects.
     *
     * @param person        The list of Person objects.
     * @param medicalRecord The list of MedicalRecord objects.
     * @return A list of PersonInfo objects representing person information from the
     *         input data.
     */
    public List<PersonInfoAlert> getPersonInfo(Person person, MedicalRecord medicalRecord, Household household) {
        List<PersonInfoAlert> personsInfo = new ArrayList<>();
        PersonInfoAlert personInfo = new PersonInfoAlert();
        personInfo.setLastName(person.getLastName());
        personInfo.setAge(beanService.convertBirthdateToAge(medicalRecord.getBirthdate()));
        personInfo.setMail(person.getEmail());
        personInfo.setMedications(medicalRecord.getMedications());
        personInfo.setAllergies(medicalRecord.getAllergies());
        personInfo.setAddress(household.getAddress());
        personsInfo.add(personInfo);
        return personsInfo;
    }
}