package com.safetynet.alerts.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.web.dao.FirestationDao;
import com.safetynet.alerts.web.model.Firestations;

/* import io.swagger.annotations.Api;

@Api("API for CRUD operations on firestations.") */
@RestController
@RequestMapping("/firestation")
public class FirestationController {

    private final FirestationDao firestationDao;

    public FirestationController(FirestationDao firestationDao) {
        this.firestationDao = firestationDao;
    }

    @GetMapping("")
    public List<Firestations> listFirestations() {
        return firestationDao.findAll();
    }

}
