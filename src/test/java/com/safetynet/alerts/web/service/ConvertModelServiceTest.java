package com.safetynet.alerts.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.web.deserialization.model.FirestationDeserialization;
import com.safetynet.alerts.web.deserialization.model.MedicalRecordDeserialization;
import com.safetynet.alerts.web.deserialization.model.PersonDeserialization;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

@ExtendWith(MockitoExtension.class)
public class ConvertModelServiceTest {

  @InjectMocks
  private ConvertModelService convertModelService;

  // Example of addresses or stations :
  private String ADDRESS_1, ADDRESS_2, ADDRESS_3, STATION_1, STATION_2;

  // Example of JSON entries :
  private List<FirestationDeserialization> firestationDeserializations;
  private FirestationDeserialization firestationDeserialization_1, firestationDeserialization_2;
  private List<PersonDeserialization> personsDeserialization;
  private PersonDeserialization personsDeserialization_1, personsDeserialization_3;
  private List<MedicalRecordDeserialization> medicalRecordsDeserializations;
  private MedicalRecordDeserialization medicalRecordDeserialization_1, medicalRecordDeserialization_3;

  // Example of converted data :
  private List<Household> households;
  private Household household_1, household_2, household_3;
  private List<Integer> idHouseholds_firestation_1, idHouseholds_firestation_2;
  private List<Person> persons;
  private Person person_1, person_3;

  /**
   * SomeJavadoc.
   * 
   * Creates data examples to carry out tests
   */
  @BeforeEach
  public void setUp() {
    // Set Adrresses :
    ADDRESS_1 = "ici";
    ADDRESS_2 = "pas ici";
    ADDRESS_3 = "Encore pas ici";
    STATION_1 = "4";
    STATION_2 = "1";

    // Set Firestation entries data :
    firestationDeserializations = new ArrayList<>();
    firestationDeserialization_1 = new FirestationDeserialization(0, ADDRESS_1,
        STATION_1);
    firestationDeserialization_2 = new FirestationDeserialization(0, ADDRESS_2,
        STATION_2);
    firestationDeserializations.add(firestationDeserialization_1);
    firestationDeserializations.add(firestationDeserialization_2);

    // Set Person entries data :
    personsDeserialization = new ArrayList<>();
    personsDeserialization_1 = new PersonDeserialization(0, "Quentin", "Beraud", ADDRESS_1, "Lyon", "69000",
        "000",
        "qbe@yahoo.com");
    personsDeserialization_3 = new PersonDeserialization(0, "George", "Galby", ADDRESS_3, "Astrub", "66666",
        "000",
        "kama@yahoo.com");
    personsDeserialization.add(personsDeserialization_1);
    personsDeserialization.add(personsDeserialization_3);

    // Set MedicalRecord entries data :
    medicalRecordsDeserializations = new ArrayList<>();
    medicalRecordDeserialization_1 = new MedicalRecordDeserialization(0, "Quentin", "Beraud", "31/12/1997",
        null,
        null);
    medicalRecordDeserialization_3 = new MedicalRecordDeserialization(0, "George", "Galby", "01/01/4988",
        null,
        null);
    medicalRecordsDeserializations.add(medicalRecordDeserialization_1);
    medicalRecordsDeserializations.add(medicalRecordDeserialization_3);

    // Set Persons example data corresponding :
    persons = new ArrayList<>();
    person_1 = new Person(1, 12, "Quentin", "Beraud", "Lyon", "69000", "000", "qbe@yahoo.com");
    person_3 = new Person(3, 2, "George", "Galby", "Astrub", "66666", "000", "kama@yahoo.com");
    persons.add(person_1);
    persons.add(person_3);

    // Set Households example data corresponding :
    households = new ArrayList<>();
    household_1 = new Household(12, ADDRESS_1);
    household_2 = new Household(5, ADDRESS_2);
    household_3 = new Household(2, ADDRESS_3);
    households.add(household_1);
    households.add(household_2);
    households.add(household_3);

    // Set id of households corresponding :
    idHouseholds_firestation_1 = new ArrayList<>();
    idHouseholds_firestation_1.add(household_1.getId());
    idHouseholds_firestation_2 = new ArrayList<>();
    idHouseholds_firestation_2.add(household_2.getId());
  }

  @Test
  void testGetFirestations() {
    List<Firestation> firestationsExpected = new ArrayList<>();
    Firestation firestation_1 = new Firestation(0, idHouseholds_firestation_1, STATION_1);
    Firestation firestation_2 = new Firestation(0, idHouseholds_firestation_2, STATION_2);
    firestationsExpected.add(firestation_1);
    firestationsExpected.add(firestation_2);

    List<Firestation> firestationsResult = convertModelService.getFirestations(firestationDeserializations,
        households);
    assertEquals(firestationsExpected, firestationsResult);
  }

  @Test
  void testGetHouseholds() {
    List<Household> householdsExpected = new ArrayList<>();
    household_1.setId(0);
    household_2.setId(0);
    household_3.setId(0);
    householdsExpected.add(household_1);
    householdsExpected.add(household_2);
    householdsExpected.add(household_3);

    List<Household> householdsResult = convertModelService.getHouseholds(personsDeserialization,
        firestationDeserializations);
    assertEquals(householdsExpected, householdsResult);
  }

  @Test
  void testGetMedicalRecords() {
    List<MedicalRecord> medicalRecordsExpected = new ArrayList<>();
    MedicalRecord medicalRecord_1 = new MedicalRecord(0, 1, "31/12/1997", null, null);
    MedicalRecord medicalRecord_3 = new MedicalRecord(0, 3, "01/01/4988", null, null);
    medicalRecordsExpected.add(medicalRecord_1);
    medicalRecordsExpected.add(medicalRecord_3);

    List<MedicalRecord> medicalRecordsResult = convertModelService.getMedicalRecords(
        medicalRecordsDeserializations,
        persons);
    assertEquals(medicalRecordsExpected, medicalRecordsResult);
  }

  @Test
  void testGetPersons() {
    List<Person> personsExpected = new ArrayList<>();
    person_1.setId(0);
    person_3.setId(0);
    personsExpected.add(person_1);
    personsExpected.add(person_3);

    List<Person> personResult = convertModelService.getPersons(personsDeserialization, households);
    assertEquals(personsExpected, personResult);
  }
}