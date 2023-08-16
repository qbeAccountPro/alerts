package com.safetynet.alerts.web.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.service.MedicalRecordService;
import org.junit.jupiter.api.BeforeEach;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordControllerTest {

    @Mock
    private MedicalRecordService medicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;

    private String FIRSTNAME = "Quentin";
    private String LASTNAME = "Beraud";
    private String BIRTHDATE = "31/02/2013";
    private MedicalRecord medicalRecord;
    private MedicalRecord oldMedicalRecord;
    private MedicalRecord newMedicalRecord;

    @BeforeEach
    public void setUp() {
        medicalRecord = new MedicalRecord(1, FIRSTNAME, LASTNAME, BIRTHDATE, null, null);
        oldMedicalRecord = medicalRecord;
        newMedicalRecord = new MedicalRecord(1, FIRSTNAME, LASTNAME, "26/02/1998", null, null);
    }


    /**
     * SomeJavadoc.
     * Test case for adding a medical record.
     */
    @Test
    void testAddMedicalRecord() {
        medicalRecordController.addMedicalRecord(medicalRecord);
        verify(medicalRecordService, times(1)).saveMedicalRecord(medicalRecord);
    }

    /**
     * SomeJavadoc.
     * Test case for deleting a medical record.
     */
    @Test
    void testDeleteMedicalRecord() {
        medicalRecordController.deleteMedicalRecord(FIRSTNAME, LASTNAME);
        verify(medicalRecordService, times(1)).deleteMedicalRecordByFirstNameAndLastName(FIRSTNAME, LASTNAME);
    }

    /**
     * SomeJavadoc.
     * Test case for updating a medical record.
     */
    @Test
    void testUpdateMedicalRecord() {
        when(medicalRecordService.findMedicalRecordByFirstNameAndLastName(FIRSTNAME, LASTNAME))
                .thenReturn(oldMedicalRecord);
        medicalRecordController.updateMedicalRecord(FIRSTNAME, LASTNAME, newMedicalRecord);
        verify(medicalRecordService, times(1)).saveMedicalRecord(newMedicalRecord);
    }
}
