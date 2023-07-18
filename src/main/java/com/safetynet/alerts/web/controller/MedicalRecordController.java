package com.safetynet.alerts.web.controller;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.service.BeanService;
import com.safetynet.alerts.web.service.MedicalRecordService;

@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    @GetMapping("")
    public List<MedicalRecord> getAllMedicalRecord() {
        return medicalRecordService.getAllMedicalRecord();
    }

    @PostMapping("")
    public void addMedicalRecord(@RequestBody MedicalRecord medicalrecord) {
        medicalRecordService.saveMedicalRecord(medicalrecord);
    }

    @PutMapping("/{firstName}/{lastName}")
    public void updateMedicalRecord(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName, @RequestBody MedicalRecord newMedicalRecord) {
        MedicalRecord oldMedicalrecord = medicalRecordService.findMedicalRecordByFirstNameAndLastName(firstName, lastName);
        if (oldMedicalrecord != null) {
            try {
                MedicalRecord updatMedicalrecord = BeanService
                        .updateBeanWithNotNullPropertiesFromNewObject(oldMedicalrecord, newMedicalRecord);
                updatMedicalrecord.setId(oldMedicalrecord.getId());
                updatMedicalrecord.setFirstName(firstName);
                updatMedicalrecord.setLastName(lastName);
            } catch (Exception e) {
                System.out.println("updateByFirstNameAndLastName produit l'erreur : " + e);
            }
        }
    }

    @Transactional
    @DeleteMapping("/{firstName}/{lastName}")
    public void deleteMedicalRecord(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName) {
        medicalRecordService.deleteMedicalRecordByFirstNameAndLastName(firstName, lastName);
    }
}
