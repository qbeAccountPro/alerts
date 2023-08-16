package com.safetynet.alerts.web.service;

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

import com.safetynet.alerts.web.dao.MedicalRecordDao;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

    @Mock
    private MedicalRecordDao medicalRecordDao;

    @InjectMocks
    private MedicalRecordService medicalRecordService;

    private List<Person> persons;
    private Person person_1, person_2;
    private List<MedicalRecord> medicalRecords, exceptedMedicalRecords;
    private MedicalRecord medicalRecord_1, medicalRecord_2;

    @BeforeEach
    public void setUp() {
        person_1 = new Person(1, "Quentin", "Beraud", "ici", "Lyon", "69000", "06 66 67 72 23", "qbe.pro@yahoo");
        person_2 = new Person(2, "Quentin_2", "Beraud_2", "pas ici", "Lyon", "69000", "06 66 67 72 23",
                "qbe.pro@yahoo");
        persons = new ArrayList<>();
        persons.add(person_1);
        persons.add(person_2);

        medicalRecord_1 = new MedicalRecord(1, "Quentin", "Beraud", "12/31/1997", null, null);
        medicalRecord_2 = new MedicalRecord(1, "Quentin_2", "will", "12/31/2020", null, null);
        medicalRecords = new ArrayList<>();
        medicalRecords.add(medicalRecord_1);
        medicalRecords.add(medicalRecord_2);

        exceptedMedicalRecords = new ArrayList<>();
        exceptedMedicalRecords.add(medicalRecord_1);
    }

    /**
     * SomeJavadoc.
     * Test for 'getAllMedicalRecord' method.
     */
    @Test
    void testGetAllMedicalRecord() {
        medicalRecordService.getAllMedicalRecord();
        verify(medicalRecordDao, times(1)).findAll();
    }

    /**
     * SomeJavadoc.
     * Test for 'getMedicalRecordsByPersons' method.
     */
    @Test
    void testGetMedicalRecordsByPersons() {
        List<MedicalRecord> actualMedicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons,
                medicalRecords);
        assertEquals(exceptedMedicalRecords, actualMedicalRecords);
    }

}
