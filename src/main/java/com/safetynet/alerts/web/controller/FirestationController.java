package com.safetynet.alerts.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.web.dao.FirestationDao;
import com.safetynet.alerts.web.model.Firestation;

import io.swagger.annotations.Api;

@Api("API for CRUD operations on firestations.")
@RestController
@RequestMapping("/firestation")
public class FirestationController {

    private final FirestationDao firestationDao;

    public FirestationController(FirestationDao firestationDao) {
        this.firestationDao = firestationDao;
    }

    @GetMapping("")
    public List<Firestation> listFirestations() {
        return firestationDao.findAll();
    }

}
