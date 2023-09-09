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

import com.safetynet.alerts.web.communUtilts.DataManipulationUtils;
import com.safetynet.alerts.web.deserialization.model.MedicalRecordDeserialization;
import com.safetynet.alerts.web.logging.EndpointsLogger;
import com.safetynet.alerts.web.model.MedicalRecord;
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
  private final DataManipulationUtils beanService;

  private EndpointsLogger log = new EndpointsLogger();

  public MedicalRecordController(MedicalRecordService medicalRecordService) {
    this.medicalRecordService = medicalRecordService;
    this.beanService = new DataManipulationUtils();
  }

  /**
   * Some javadoc.
   * 
   * Adds a new medical record to the system.
   *
   * @param medicalrecordDeserialize A MedicalRecord object to be converted.
   */
  @PostMapping("")
  public ResponseEntity<String> addMedicalRecord(
      @RequestBody MedicalRecordDeserialization medicalrecordDeserialize) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
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
   * Updates an existing medicalRecord object for a specific person.
   *
   * @param firstName                    of the person.
   * @param lastName                     of the person.
   * @param medicalrecordDeserialization The updated MedicalRecord object in the
   *                                     deserialization format.
   */
  @PutMapping("/{firstName}/{lastName}")
  public ResponseEntity<String> updateMedicalRecord(@PathVariable("firstName") String firstName,
      @PathVariable("lastName") String lastName,
      @RequestBody MedicalRecordDeserialization medicalrecordDeserialization) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName, firstName, lastName);

    // Check the request content :
    Boolean fieldsAreNull = beanService.areFieldsNullExceptId(medicalrecordDeserialization);
    if (fieldsAreNull) {
      return log.incorrectContent(methodeName);
    } else {
      return medicalRecordService.updateMedicalRecord(firstName, lastName, medicalrecordDeserialization,
          methodeName);
    }
  }

  /**
   * Some javadoc.
   * 
   * Deletes a medical record based on the first name and last name of the person.
   * 
   *
   * @param firstName of the person.
   * @param lastName  of the person.
   */
  @Transactional
  @DeleteMapping("/{firstName}/{lastName}")
  public ResponseEntity<String> deleteMedicalRecord(@PathVariable("firstName") String firstName,
      @PathVariable("lastName") String lastName) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName, firstName, lastName);

    return medicalRecordService.deleteMedicalRecord(firstName, lastName, methodeName);
  }

  /**
   * Some javadoc.
   * 
   * Get all MedicalRecord objetcs.
   */
  @GetMapping("/all")
  public List<MedicalRecord> getAllMedicalRecords() {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);
    return medicalRecordService.getAllMedicalRecords();
  }
}