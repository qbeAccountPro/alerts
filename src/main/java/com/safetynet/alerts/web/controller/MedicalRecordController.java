package com.safetynet.alerts.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.web.deserialization.model.MedicalRecordDeserialization;
import com.safetynet.alerts.web.logging.EndpointsLogger;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.service.BeanService;
import com.safetynet.alerts.web.service.MedicalRecordService;

/**
 * Some javadoc.
 * 
 * This class represents a REST controller for managing medicalRecords.
 * 
 * It provides endpoints for retrieving all medical records, adding new medical
 * records, updating existing medical records, and deleting medical records.
 */
@RestController
@RequestMapping("/medicalRecord")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;
    private final BeanService beanService;

    private EndpointsLogger log = new EndpointsLogger();

    public MedicalRecordController(MedicalRecordService medicalRecordService) {
        this.medicalRecordService = medicalRecordService;
        this.beanService = new BeanService();
    }

    /**
     * Some javadoc.
     * 
     * Adds a new medical record to the system.
     *
     * @param medicalrecordDeserialize A MedicalRecord object.
     */
    @PostMapping("")
    public ResponseEntity<String> addMedicalRecord(
            @RequestBody MedicalRecordDeserialization medicalrecordDeserialize) {
        // Log the request :
        String methodeName = BeanService.getCurrentMethodName();
        log.request(methodeName);

        // Check the content request :
        Boolean fieldsAreNull = beanService.areFieldsNullExceptId(medicalrecordDeserialize);
        if (fieldsAreNull) {
            return log.incorrectContent(methodeName);
        } else {
            return medicalRecordService.addMedicalRecord(medicalrecordDeserialize, methodeName);
        }
    }

    /**
     * Some javadoc.
     * 
     * WARNING : For the moment we cannot differentiate medical files if two people
     * have the same name in a learning logic and not in a real case they will
     * both be modified! In the future a telephone number or a birthday date should
     * be added to both entities, to make the connection.
     * 
     * Updates an existing medicalRecord object for a specific person.
     *
     * @param firstName
     * @param lastName
     * @param medicalrecordDeserialization The updated MedicalRecord object in the
     *                                     deserialization format.
     */
    @PutMapping("/{firstName}/{lastName}")
    public ResponseEntity<String> updateMedicalRecord(@PathVariable("firstName") String firstName,
            @PathVariable("lastName") String lastName,
            @RequestBody MedicalRecordDeserialization medicalrecordDeserialization) {
        // Log the request :
        String methodeName = BeanService.getCurrentMethodName();
        log.request(methodeName, firstName, lastName);

        // Check the request content :
        Boolean fieldsAreNull = beanService.areFieldsNullExceptId(medicalrecordDeserialization);
        if (fieldsAreNull) {
            return log.incorrectContent(methodeName);
        } else {
            return medicalRecordService.updateMedicalRecord(firstName,lastName,medicalrecordDeserialization, methodeName);
        }
    }

    /**
     * Some javadoc.
     * 
     * Deletes a medical record based on the first name and last name of the person.
     * 
     * WARNING : As same at the update method, we cannot differentiate medical files
     * if two people
     * have the same name in a learning logic and not in a real case they will
     * both be modified! In the future a telephone number or a birthday date should
     * be added to both entities, to make the connection.
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
        // Log the request :
        String methodeName = BeanService.getCurrentMethodName();
        log.request(methodeName, firstName, lastName);

        return medicalRecordService.deleteMedicalRecord(firstName, lastName, methodeName);
    }

    @GetMapping("/all")
    public List<MedicalRecord> getAllMedicalRecords() {
        return medicalRecordService.getAllMedicalRecords();
    }
}
