package com.safetynet.alerts.web.controller;


import org.tinylog.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.service.BeanService;
import com.safetynet.alerts.web.service.MedicalRecordService;

/**
 * Some javadoc.
 * This class represents a REST controller for managing medicalRecords.
 * It provides endpoints for retrieving all medical records, adding new medical
 * records, updating existing medical records, and deleting medical records.
 */
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {


    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
    }

    /**
     * Some javadoc.
     * Adds a new medical record to the system.
     *
     * @param medicalrecord The MedicalRecord object representing the new medical
     *                      record to be added.
     */
    @PostMapping("")
    public void addMedicalRecord(@RequestBody MedicalRecord medicalrecord) {
        medicalRecordService.saveMedicalRecord(medicalrecord);
        Logger.info("Request : " + BeanService.getCurrentMethodName() + "().");
    }

    /**
     * Some javadoc.
     * Updates an existing medical record for a specific person.
     *
     * @param firstName        The first name of the person whose medical record is
     *                         to be updated.
     * @param lastName         The last name of the person whose medical record is
     *                         to be updated.
     * @param newMedicalRecord The updated MedicalRecord object with the new medical
     *                         information.
     */
    @PutMapping("/{firstName}/{lastName}")
    public void updateMedicalRecord(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName, @RequestBody MedicalRecord newMedicalRecord) {
        MedicalRecord oldMedicalrecord = medicalRecordService.findMedicalRecordByFirstNameAndLastName(firstName,
                lastName);
        if (oldMedicalrecord != null) {
            try {
                MedicalRecord updatMedicalrecord = BeanService
                        .updateBeanWithNotNullPropertiesFromNewObject(oldMedicalrecord, newMedicalRecord);
                updatMedicalrecord.setId(oldMedicalrecord.getId());
                updatMedicalrecord.setFirstName(firstName);
                updatMedicalrecord.setLastName(lastName);
                medicalRecordService.saveMedicalRecord(updatMedicalrecord);
                Logger.info("Request : " + BeanService.getCurrentMethodName() + " with firstName and lastName : " + firstName
                        + " " + lastName + ".");
            } catch (Exception e) {
                Logger.error("Request : " + BeanService.getCurrentMethodName() + " with firstName and lastName : " + firstName
                        + " " + lastName + ", genereted this exception : " + e);
            }
        } else {
            Logger.error("Request : " + BeanService.getCurrentMethodName() + " with firstName and lastName : " + firstName
                    + " " + lastName + ".");
        }
    }

    /**
     * Some javadoc.
     * Deletes a medical record based on the first name and last name of the person.
     *
     * @param firstName The first name of the person whose medical record is to be
     *                  deleted.
     * @param lastName  The last name of the person whose medical record is to be
     *                  deleted.
     */
    @Transactional
    @DeleteMapping("/{firstName}/{lastName}")
    public void deleteMedicalRecord(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName) {
        medicalRecordService.deleteMedicalRecordByFirstNameAndLastName(firstName, lastName);
        Logger.info("Request : " + BeanService.getCurrentMethodName() + " with firstName and lastName : " + firstName
                + " " + lastName + ".");
    }
}
