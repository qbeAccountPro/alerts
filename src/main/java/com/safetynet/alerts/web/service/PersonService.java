package com.safetynet.alerts.web.service;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.dao.PersonDao;
import com.safetynet.alerts.web.model.Person;

import java.util.List;

@Service
public class PersonService {
    
    private PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public Iterable<Person> List(){
        return personDao.findAll();
    }

    public Person save(Person person){
        return personDao.save(person);
    }

    public Iterable<Person> save(List<Person> persons){
        return personDao.saveAll(persons);
    }


}
