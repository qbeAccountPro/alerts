package com.safetynet.alerts.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.dao.PersonDao;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Autowired
    private final PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public List<Person> getAllPersons() {
        return personDao.findAll();
    }

    public Person savePerson(Person person) {
        return personDao.save(person);
    }

    public List<Person> getPersonsByAddresses(List<String> addresses) {
        List<Person> persons = getAllPersons();
        List<Person> residents = new ArrayList<>();
        for (Person person : persons) {
            for (String address : addresses) {
                if (BeanService.normalizeString(person.getAddress()).equalsIgnoreCase(BeanService
                        .normalizeString(address))) {
                    residents.add(person);
                    break;
                }
            }
        }
        return residents;
    }

    public List<Person> getPersonsListByAddress(String address) {
        List<Person> persons = getAllPersons();
        List<Person> residents = new ArrayList<>();
        for (Person person : persons) {
            if (BeanService.normalizeString(person.getAddress()).equalsIgnoreCase(BeanService
                    .normalizeString(address))) {
                residents.add(person);
            }
        }
        return residents;
    }

    public Person getPersonById(int id) {
        return personDao.findById(id);
    }

    public Person findPersonByFirstNameAndLastName(String firstName, String lastName) {
        return personDao.findByFirstNameAndLastName(firstName, lastName);
    }

    public void deletePersonById(int id) {
        personDao.deleteById(id);
    }

    public List<Person> getChildren(List<Person> persons,
            List<MedicalRecord> medicalRecords) {
        MedicalRecordService medicalRecordService = new MedicalRecordService(null);
        List<Person> children = new ArrayList<>();
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                Boolean checkFirstName = person.getFirstName().equals(medicalRecord.getFirstName());
                Boolean checkLastName = person.getLastName().equals(medicalRecord.getLastName());
                Boolean checkMinor = medicalRecordService.isMinor(medicalRecord.getBirthdate());
                if (checkFirstName && checkLastName && checkMinor) {
                    children.add(person);
                    break;
                }
            }
        }
        return children;
    }

    public List<Person> getAdults(List<Person> persons, List<MedicalRecord> medicalRecords) {
        MedicalRecordService medicalRecordService = new MedicalRecordService(null);
        List<Person> adults = new ArrayList<>();
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                Boolean checkFirstName = person.getFirstName().equals(medicalRecord.getFirstName());
                Boolean checkLastName = person.getLastName().equals(medicalRecord.getLastName());
                Boolean checkMinor = medicalRecordService.isMinor(medicalRecord.getBirthdate());
                if (checkFirstName && checkLastName && !checkMinor) {
                    adults.add(person);
                    break;
                }
            }
        }
        return adults;
    }

    public List<Person> getPersonsByCity(String city) {
        List<Person> persons = getAllPersons();
        List<Person> residents = new ArrayList<>();
        for (Person person : persons) {
            Boolean checkCity = person.getCity().equals(city);
            if (checkCity) {
                residents.add(person);
            }
        }
        return residents;
    }

    public List<Person> getPersonsByFirstNameAndLastName(String firstName, String lastName) {
        List<Person> persons = getAllPersons();
        List<Person> matchingPersons = new ArrayList<>();
        for (Person person : persons) {
            Boolean checkFirstName = person.getFirstName().equals(firstName);
            Boolean checkLastName = person.getLastName().equals(lastName);
            if (checkFirstName && checkLastName) {
                matchingPersons.add(person);
            }
        }
        return matchingPersons;
    }
}