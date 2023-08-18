package com.safetynet.alerts.web.controller;

import org.tinylog.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private BeanService beanService = new BeanService();

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
    public ResponseEntity<String> addMedicalRecord(@RequestBody MedicalRecord medicalrecord) {
        Logger.info("Request " + BeanService.getCurrentMethodName() + ".");
        Boolean areFieldsAreNull = beanService.areFieldsNullExceptId(medicalrecord);
        if (areFieldsAreNull) {
            Logger.error("Answer " + BeanService.getCurrentMethodName() + " : content is incorrect.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Medicalrecord content is incorrect.");
        } else {
            medicalRecordService.saveMedicalRecord(medicalrecord);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : medicalrecord added successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body("Medicalrecord added successfully.");
        }

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
    public ResponseEntity<String> updateMedicalRecord(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName, @RequestBody MedicalRecord newMedicalRecord) {
        Logger.info("Request " + BeanService.getCurrentMethodName() + " : with this first and last name = " + firstName
                + " "
                + lastName + ".");
        Boolean areFieldsAreNull = beanService.areFieldsNullExceptId(newMedicalRecord);
        if (areFieldsAreNull) {
            Logger.error("Answer " + BeanService.getCurrentMethodName() + " : content is incorrect.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("MedicalRecord content is incorrect.");
        } else {
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
                    Logger.info(
                            "Answer " + BeanService.getCurrentMethodName() + " : modified successfully.");
                    return ResponseEntity.status(HttpStatus.OK).body("MedicalRecord modified successfully.");
                } catch (Exception e) {
                    Logger.error("Answer " + BeanService.getCurrentMethodName() + " : threw an exception.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request threw an exception.");
                }
            } else {
                Logger.error("Answer " + BeanService.getCurrentMethodName() + " : first and last name doesn't match.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("First and last name doesn't match.");
            }
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
    public ResponseEntity<String> deleteMedicalRecord(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName) {
        Logger.info(
                "Request " + BeanService.getCurrentMethodName() + " : with this first and last name  = " + firstName
                        + " " + lastName + ".");
        MedicalRecord oldMedicalrecord = medicalRecordService.findMedicalRecordByFirstNameAndLastName(firstName,
                lastName);
        if (oldMedicalrecord != null) {
            medicalRecordService.deleteMedicalRecordByFirstNameAndLastName(firstName, lastName);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body("MedicalRecord deleted successfully.");
        } else {
            Logger.error("Answer " + BeanService.getCurrentMethodName() + " : doesn't match with any medicalRecord.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MedicalRecord address doesn't match.");
        }
    }
}
