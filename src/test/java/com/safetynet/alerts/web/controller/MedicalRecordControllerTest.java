package com.safetynet.alerts.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.safetynet.alerts.web.deserialization.model.MedicalRecordDeserialization;
import com.safetynet.alerts.web.service.MedicalRecordService;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordControllerTest {
  @Mock
  private MedicalRecordService medicalRecordService;

  @InjectMocks
  private MedicalRecordController medicalRecordController;

  private String FIRSTNAME = "Quentin";
  private String LASTNAME = "Beraud";
  private MedicalRecordDeserialization medicalRecord, medicalRecordWithEmptyField;

  @BeforeEach
  public void setUp() {
    List<String> medication = new ArrayList<String>();
    List<String> allergie = new ArrayList<String>();
    medicalRecord = new MedicalRecordDeserialization(1, FIRSTNAME, LASTNAME, "31/02/2013", medication, allergie);
    medicalRecordWithEmptyField = new MedicalRecordDeserialization(1, FIRSTNAME, LASTNAME, null, medication, allergie);

  }

  @Test
  void testAddMedicalRecord() {
    medicalRecordController.addMedicalRecord(medicalRecord);
    verify(medicalRecordService, times(1)).addMedicalRecord(medicalRecord, "addMedicalRecord");
  }

  @Test
  void testAddMedicalRecordWithEmptyField() {
    ResponseEntity<String> result = medicalRecordController.addMedicalRecord(medicalRecordWithEmptyField);

    ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect content.");
    assertEquals(expected, result);
    verify(medicalRecordService, times(0)).addMedicalRecord(medicalRecord, "addMedicalRecord");
  }

  @Test
  void testDeleteMedicalRecord() {
    medicalRecordController.deleteMedicalRecord(FIRSTNAME, LASTNAME);
    verify(medicalRecordService, times(1)).deleteMedicalRecord(FIRSTNAME, LASTNAME, "deleteMedicalRecord");
  }

  @Test
  void testGetAllMedicalRecords() {
    medicalRecordController.getAllMedicalRecords();
    verify(medicalRecordService, times(1)).getAllMedicalRecords();
  }

  @Test
  void testUpdateMedicalRecord() {
    medicalRecordController.updateMedicalRecord(FIRSTNAME, LASTNAME, medicalRecord);
    verify(medicalRecordService, times(1)).updateMedicalRecord(FIRSTNAME, LASTNAME, medicalRecord,
        "updateMedicalRecord");
  }

  @Test
  void testUpdateMedicalRecordWithEmptyField() {
    medicalRecordController.updateMedicalRecord(FIRSTNAME, LASTNAME, medicalRecordWithEmptyField);
    verify(medicalRecordService, times(0)).updateMedicalRecord(FIRSTNAME, LASTNAME, medicalRecord,
        "updateMedicalRecord");
  }
}