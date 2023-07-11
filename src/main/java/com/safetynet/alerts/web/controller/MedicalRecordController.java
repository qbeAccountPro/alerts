package com.safetynet.alerts.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.web.dao.MedicalrecordDao;
import com.safetynet.alerts.web.model.Medicalrecord;

import io.swagger.annotations.Api;

@Api("API for CRUD operations on medicals records.")
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final MedicalrecordDao medicalrecordDao;

    public MedicalRecordController(MedicalrecordDao medicalrecordDao) {
        this.medicalrecordDao = medicalrecordDao;
    }

        @GetMapping("")
    public List<Medicalrecord> listMedicalRecord() {
        return medicalrecordDao.findAll();
    }

}
