package com.safetynet.alerts.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.web.dao.PersonDao;
import com.safetynet.alerts.web.model.Person;

import io.swagger.annotations.Api;

@Api("API for CRUD operations on persons.")
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonDao personDao;

    public PersonController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @GetMapping("")
    public List<Person> listPerson() {
        return personDao.findAll();
    }

}
