package com.safetynet.alerts.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.safetynet.alerts.web.dao.MedicalRecordDao;
import com.safetynet.alerts.web.deserialization.model.MedicalRecordDeserialization;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

  @Mock
  private MedicalRecordDao medicalRecordDao;

  @Mock
  private PersonService personService;

  @InjectMocks
  private MedicalRecordService medicalRecordService;

  private String FIRSTNAME = "Quentin";
  private String LASTNAME = "Beraud";
  private MedicalRecordDeserialization medicalRecordDeserialization;
  private MedicalRecord medicalRecord;
  private List<MedicalRecord> childMedicalRecords, adultMedicalRecords;
  private Person person;

  @BeforeEach
  public void setUp() {
    List<String> medication = new ArrayList<String>();
    List<String> allergie = new ArrayList<String>();
    medicalRecordDeserialization = new MedicalRecordDeserialization(1, FIRSTNAME, LASTNAME, "02/12/1998",
        medication, allergie);
    medicalRecord = new MedicalRecord(2, 1, medicalRecordDeserialization.getBirthdate(), medication, allergie);
    childMedicalRecords = new ArrayList<>();
    adultMedicalRecords = new ArrayList<>();
    adultMedicalRecords.add(medicalRecord);
    person = new Person(1, 12, FIRSTNAME, LASTNAME, "Lyon", "69000", "06 00 00 00 00", "qda@yahoo.com");
  }

  @Test
  void testAddMedicalRecordSuccessfully() {
    medicalRecord.setId(0);

    when(personService.getPersonByFirstAndLastName(FIRSTNAME, LASTNAME)).thenReturn(person);
    when(medicalRecordDao.findByIdPerson(1)).thenReturn(null);

    ResponseEntity<String> result = medicalRecordService.addMedicalRecord(medicalRecordDeserialization, FIRSTNAME);
    ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.CREATED).body("Content added successfully.");

    verify(medicalRecordDao, times(1)).save(medicalRecord);
    assertEquals(expected, result);
  }

  @Test
    void testAddMedicalRecordWithInexistingPerson() {
        when(personService.getPersonByFirstAndLastName(FIRSTNAME, LASTNAME)).thenReturn(null);

        ResponseEntity<String> result = medicalRecordService.addMedicalRecord(medicalRecordDeserialization, FIRSTNAME);
        ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Argument has no match.");

        verify(medicalRecordDao, times(0)).save(medicalRecord);
        assertEquals(expected, result);
    }

  @Test
    void testAddMedicalRecordWithExistingMedicalRecord() {
        when(personService.getPersonByFirstAndLastName(FIRSTNAME, LASTNAME)).thenReturn(person);
        when(medicalRecordDao.findByIdPerson(1)).thenReturn(medicalRecord);

        ResponseEntity<String> result = medicalRecordService.addMedicalRecord(medicalRecordDeserialization, FIRSTNAME);
        ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.CONFLICT).body("Mapping already exist.");

        verify(medicalRecordDao, times(0)).save(medicalRecord);
        assertEquals(expected, result);
    }

  @Test
    void testDeleteMedicalRecordSuccessfully() {
        when(personService.getPersonByFirstAndLastName(FIRSTNAME, LASTNAME)).thenReturn(person);
        when(medicalRecordDao.findByIdPerson(1)).thenReturn(medicalRecord);

        ResponseEntity<String> result = medicalRecordService.deleteMedicalRecord(FIRSTNAME, LASTNAME, "deleteMedicalRecord");
        ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.OK).body("Content deleted successfully.");

        assertEquals(expected, result);
        verify(medicalRecordDao, times(1)).deleteById(medicalRecord.getId());
    }

  @Test
    void testDeleteMedicalRecordWithInexistingPerson() {
        when(personService.getPersonByFirstAndLastName(FIRSTNAME, LASTNAME)).thenReturn(null);

        ResponseEntity<String> result = medicalRecordService.deleteMedicalRecord(FIRSTNAME, LASTNAME, "deleteMedicalRecord");
        ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Argument has no match.");

    assertEquals(expected, result);
        verify(medicalRecordDao, times(0)).deleteById(medicalRecord.getId());
    }

  @Test
    void testDeleteMedicalRecordWithInexistingMedicalRecord() {
        when(personService.getPersonByFirstAndLastName(FIRSTNAME, LASTNAME)).thenReturn(person);
        when(medicalRecordDao.findByIdPerson(1)).thenReturn(null);

        ResponseEntity<String> result = medicalRecordService.deleteMedicalRecord(FIRSTNAME, LASTNAME, "deleteMedicalRecord");
        ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Argument has no match.");

        assertEquals(expected, result);
        verify(medicalRecordDao, times(0)).deleteById(medicalRecord.getId());
    }

  @Test
  void testGetAdultsMedicalRecords() {
    List<MedicalRecord> result = medicalRecordService.getAdultsMedicalRecords(adultMedicalRecords);
    assertEquals(adultMedicalRecords, result);
  }

  @Test
  void testGetAdultsNumber() {
    int results = medicalRecordService.getAdultsNumber(adultMedicalRecords);
    int expected = 1;
    assertEquals(expected, results);
  }

  @Test
  void testGetAllMedicalRecords() {
    medicalRecordService.getAllMedicalRecords();
    verify(medicalRecordDao, times(1)).findAll();
  }

  @Test
  void testGetChildrenMedicalRecords() {
    List<MedicalRecord> result = medicalRecordService.getChildrenMedicalRecords(adultMedicalRecords);
    assertEquals(childMedicalRecords, result);
  }

  @Test
  void testGetMedicalRecordsByPersons() {
    List<MedicalRecord> expected = new ArrayList<>();
    expected.add(medicalRecord);
    List<Person> persons = new ArrayList<>();
    persons.add(person);

    when(medicalRecordDao.findByIdPerson(person.getId())).thenReturn(medicalRecord);

    List<MedicalRecord> result = medicalRecordService.getMedicalRecordsByPersons(persons);

    assertEquals(expected, result);
    verify(medicalRecordDao, times(1)).findByIdPerson(person.getId());
  }

  @Test
  void testGetMinorsNumber() {
    int results = medicalRecordService.getMinorsNumber(adultMedicalRecords);
    int expected = 0;
    assertEquals(expected, results);
  }

  @Test
  void testIsMinor() {
    Boolean result = medicalRecordService.isMinor("02/02/2005");
    assertEquals(true, result);
  }

  @Test
  void testSaveMedicalRecord() {
    medicalRecord.setId(0);
    medicalRecordService.saveMedicalRecord(medicalRecordDeserialization, 1);

    verify(medicalRecordDao, times(1)).save(medicalRecord);
  }

  @Test
    void testUpdateMedicalRecordSuccessfully() {
    when(personService.getPersonByFirstAndLastName(FIRSTNAME, LASTNAME)).thenReturn(person);
    when(medicalRecordDao.findByIdPerson(person.getId())).thenReturn(medicalRecord);

    ResponseEntity<String> result = medicalRecordService.updateMedicalRecord(FIRSTNAME, LASTNAME, medicalRecordDeserialization, "updateMedicalRecord");
    ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.OK).body("Content updated successfully.");

    assertEquals(expected, result);
    verify(medicalRecordDao, times(1)).save(medicalRecord);
    }

  @Test
    void testUpdateMedicalRecordWithInexistingPerson() {
    when(personService.getPersonByFirstAndLastName(FIRSTNAME, LASTNAME)).thenReturn(null);

    ResponseEntity<String> result = medicalRecordService.updateMedicalRecord(FIRSTNAME, LASTNAME, medicalRecordDeserialization, "updateMedicalRecord");
    ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Argument has no match.");

    assertEquals(expected, result);
    verify(medicalRecordDao, times(0)).save(medicalRecord);
    }

  @Test
    void testUpdateMedicalRecordWithInexistingMedicalRecord() {
    when(personService.getPersonByFirstAndLastName(FIRSTNAME, LASTNAME)).thenReturn(person);
    when(medicalRecordDao.findByIdPerson(person.getId())).thenReturn(null);

    ResponseEntity<String> result = medicalRecordService.updateMedicalRecord(FIRSTNAME, LASTNAME, medicalRecordDeserialization, "updateMedicalRecord");
    ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Argument has no match.");
    
    assertEquals(expected, result);
    verify(medicalRecordDao, times(0)).save(medicalRecord);
    }
}
