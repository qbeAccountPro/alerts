package com.safetynet.alerts.web.service;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.dao.PersonDao;
import com.safetynet.alerts.web.model.Persons;

import java.util.List;

@Service
public class PersonService {
    
    private PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    public Iterable<Persons> List(){
        return personDao.findAll();
    }

    public Persons save(Persons person){
        return personDao.save(person);
    }

    public Iterable<Persons> save(List<Persons> persons){
        return personDao.saveAll(persons);
    }


}
