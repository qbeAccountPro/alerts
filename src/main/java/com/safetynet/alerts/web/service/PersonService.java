package com.safetynet.alerts.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.dao.PersonDao;
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

    public List<Person> getPersonsListByAddressesList(List<String> addresses) {
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

    public boolean checkPersonAdults(Person person) {
        return false;
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

    public List<Person> getPersonsByFirstNameAndLastName(String firstName, String lastName) {
        return null;
    }
}