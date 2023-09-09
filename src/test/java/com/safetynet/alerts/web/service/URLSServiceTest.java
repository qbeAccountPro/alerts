package com.safetynet.alerts.web.service;

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

import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.Serialization;
import com.safetynet.alerts.web.serialization.model.ChildAlert;
import com.safetynet.alerts.web.serialization.model.FireAlert;
import com.safetynet.alerts.web.serialization.model.FirestationAlert;
import com.safetynet.alerts.web.serialization.model.FloodAlert;
import com.safetynet.alerts.web.serialization.model.FloodAlertByHousehold;
import com.safetynet.alerts.web.serialization.model.PersonInfoAlert;
import com.safetynet.alerts.web.serialization.service.ChildAlertService;

@ExtendWith(MockitoExtension.class)
public class URLSServiceTest {

  @InjectMocks
  private URLSService urlsService;

  @Mock
  private HouseHoldService houseHoldService;

  @Mock
  private PersonService personService;

  @Mock
  private MedicalRecordService medicalRecordService;

  @Mock
  private FirestationService firestationService;

  @Mock
  private Serialization serialization;

  @Mock
  private ChildAlertService childAlertService;

  // Example of addresses or stations :
  private String ADDRESS_1, ADDRESS_2, ADDRESS_3, STATION_1, STATION_2, CITY_1;
  private String FIRSTNAME_1, LASTNAME_1;

  // Example of converted data :
  private List<Household> households;
  private Household household_1, household_2, household_3;
  private List<Integer> idHouseholds_firestation_1, idHouseholds_firestation_2;
  private List<Person> persons;
  private Person person_1, person_3;
  private List<Firestation> firestations;
  private Firestation firestation_1, firestation_2;
  private List<MedicalRecord> medicalRecords;
  private MedicalRecord medicalRecord_1, medicalRecord_2;

  @BeforeEach
  public void setUp() {
    // Set Adrresses :
    ADDRESS_1 = "ici";
    ADDRESS_2 = "pas ici";
    ADDRESS_3 = "Encore pas ici";
    CITY_1 = "Lyon";
    STATION_1 = "4";
    STATION_2 = "1";
    FIRSTNAME_1 = "Quentin";
    LASTNAME_1 = "Beraud";

    // Set Persons example data :
    persons = new ArrayList<>();
    person_1 = new Person(1, 12, FIRSTNAME_1, LASTNAME_1, CITY_1, "69000", "000", "qbe@yahoo.com");
    person_3 = new Person(3, 2, "George", "Galby", "Astrub", "66666", "000", "kama@yahoo.com");
    persons.add(person_1);
    persons.add(person_3);

    // Set Households example data :
    households = new ArrayList<>();
    household_1 = new Household(12, ADDRESS_1);
    household_2 = new Household(5, ADDRESS_2);
    household_3 = new Household(2, ADDRESS_3);
    households.add(household_1);
    households.add(household_2);
    households.add(household_3);

    // Set id of households :
    idHouseholds_firestation_1 = new ArrayList<>();
    idHouseholds_firestation_1.add(household_1.getId());
    idHouseholds_firestation_2 = new ArrayList<>();
    idHouseholds_firestation_2.add(household_2.getId());

    // Set Firestation example data :
    firestations = new ArrayList<>();
    firestation_1 = new Firestation(0, idHouseholds_firestation_1, STATION_1);
    firestation_2 = new Firestation(0, idHouseholds_firestation_2, STATION_2);
    firestations.add(firestation_1);
    firestations.add(firestation_2);

    // Set MedicalRecords example data :
    medicalRecords = new ArrayList<>();
    medicalRecord_1 = new MedicalRecord(1, 1, "02/03/1997", null, null);
    medicalRecord_2 = new MedicalRecord(2, 3, "03/01/1999", null, null);
    medicalRecords.add(medicalRecord_1);
    medicalRecords.add(medicalRecord_2);
  }

  @Test
  void testAllResidentsEmailsFromCity() {
    List<Person> personsFromLyon = new ArrayList<>();
    personsFromLyon.add(person_1);
    when(personService.getPersonsByCity(CITY_1)).thenReturn(personsFromLyon);

    urlsService.allResidentsEmailsFromCity(CITY_1);

    verify(serialization, times(1)).communityEmailSerialization(personsFromLyon, "allResidentsEmailsFromCity",
        CITY_1);
  }

  @Test
  void testChildrenLivingAtThisAddress() {
    List<ChildAlert> adults = new ArrayList<>();
    ChildAlert childAlert_1 = new ChildAlert("Quentin", "Beraud", 26);
    ChildAlert childAlert_2 = new ChildAlert("George", "Galby", 24);
    adults.add(childAlert_1);
    adults.add(childAlert_2);
    List<MedicalRecord> emptyMedicalRecords = new ArrayList<>();
    List<ChildAlert> emptChildAlerts = new ArrayList<>();

    when(houseHoldService.getHouseholdByAddress(ADDRESS_1)).thenReturn(household_1);
    when(personService.getPersonsByHousehold(household_1)).thenReturn(persons);
    when(medicalRecordService.getMedicalRecordsByPersons(persons)).thenReturn(medicalRecords);
    when(medicalRecordService.getAdultsMedicalRecords(medicalRecords)).thenReturn(medicalRecords);
    when(medicalRecordService.getChildrenMedicalRecords(medicalRecords)).thenReturn(emptyMedicalRecords);

    urlsService.childrenLivingAtThisAddress(ADDRESS_1);

    verify(serialization, times(1)).childAlertSerialization(emptChildAlerts, adults, "childrenLivingAtThisAddress",
        ADDRESS_1);
  }

  @Test
  void testPersonCoveredByFireStation() {
    List<Household> households_1 = new ArrayList<>();
    households_1.add(household_1);
    List<Person> persons_1 = new ArrayList<>();
    persons_1.add(person_1);
    List<MedicalRecord> medicalRecords_1 = new ArrayList<>();
    medicalRecords_1.add(medicalRecord_1);
    List<FirestationAlert> firestationAlerts = new ArrayList<>();
    FirestationAlert firestationAlert_1 = new FirestationAlert(FIRSTNAME_1, LASTNAME_1, ADDRESS_1,
        person_1.getPhone());
    firestationAlerts.add(firestationAlert_1);

    when(firestationService.getFirestationByStation(STATION_1)).thenReturn(firestation_1);
    when(houseHoldService.getHouseholdsByFirestation(firestation_1)).thenReturn(households_1);
    when(personService.getPersonsByHouseholds(households_1)).thenReturn(persons_1);
    when(medicalRecordService.getMedicalRecordsByPersons(persons_1)).thenReturn(medicalRecords_1);
    when(medicalRecordService.getAdultsNumber(medicalRecords_1)).thenReturn(1);
    when(medicalRecordService.getMinorsNumber(medicalRecords_1)).thenReturn(0);

    urlsService.personCoveredByFireStation(STATION_1);

    verify(serialization, times(1)).firestationAlertSerialization(firestationAlerts, "personCoveredByFireStation",
        STATION_1, 0, 1);
  }

  @Test
  void testPersonInfoByFirstAndLastName() {
    List<PersonInfoAlert> personInfoAlerts = new ArrayList<>();
    PersonInfoAlert personInfoAlert = new PersonInfoAlert(LASTNAME_1, ADDRESS_1, person_1.getEmail(), 26, null,
        null);
    personInfoAlerts.add(personInfoAlert);

    when(personService.getPersonByFirstAndLastName(FIRSTNAME_1, LASTNAME_1)).thenReturn(person_1);
    when(medicalRecordService.getMedicalRecordByPerson(person_1)).thenReturn(medicalRecord_1);
    when(houseHoldService.getHouseholdById(person_1.getIdHousehold())).thenReturn(household_1);

    urlsService.personInfoByFirstAndLastName(FIRSTNAME_1, LASTNAME_1);

    verify(serialization, times(1)).personInfoSerialization(personInfoAlerts, "personInfoByFirstAndLastName",
        FIRSTNAME_1, LASTNAME_1);
  }

  @Test
  void testPersonsByHouseholdsFromStation() {
    List<Household> households_1 = new ArrayList<>();
    households_1.add(household_1);
    List<Person> persons_1 = new ArrayList<>();
    persons_1.add(person_1);
    List<MedicalRecord> medicalRecords_1 = new ArrayList<>();
    medicalRecords_1.add(medicalRecord_1);
    List<FloodAlert> FloodsAlert = new ArrayList<>();
    FloodAlert floodAlert = new FloodAlert(LASTNAME_1, person_1.getPhone(), 26, null, null);
    FloodsAlert.add(floodAlert);
    List<FloodAlertByHousehold> floodsAlertByHousehold = new ArrayList<>();
    FloodAlertByHousehold floodAlertByHousehold = new FloodAlertByHousehold(household_1, FloodsAlert);
    floodsAlertByHousehold.add(floodAlertByHousehold);

    when(firestationService.getFirestationByStation(STATION_1)).thenReturn(firestation_1);
    when(houseHoldService.getHouseholdsByFirestation(firestation_1)).thenReturn(households_1);
    when(personService.getPersonsByHouseholds(households_1)).thenReturn(persons_1);
    when(medicalRecordService.getMedicalRecordsByPersons(persons_1)).thenReturn(medicalRecords_1);

    urlsService.personsByHouseholdsFromStation(STATION_1);

    verify(serialization, times(1)).floodSerialization(floodsAlertByHousehold, "personsByHouseholdsFromStation",
        STATION_1);
  }

  @Test
  void testPersonsPhoneNumbersCoveredByStation() {
    List<Household> households_1 = new ArrayList<>();
    households_1.add(household_1);
    List<Person> persons_1 = new ArrayList<>();
    persons_1.add(person_1);
    when(firestationService.getFirestationByStation(STATION_1)).thenReturn(firestation_1);
    when(houseHoldService.getHouseholdsByFirestation(firestation_1)).thenReturn(households_1);
    when(personService.getPersonsByHouseholds(households_1)).thenReturn(persons_1);
    urlsService.personsPhoneNumbersCoveredByStation(STATION_1);
    verify(serialization, times(1)).phoneAlertSerialization(persons_1, "personsPhoneNumbersCoveredByStation",
        STATION_1);
  }

  @Test
  void testStationAndPersonsByAddress() {
    FireAlert fireAlert_1 = new FireAlert("Beraud", "000", 26, null, null);
    FireAlert fireAlert_2 = new FireAlert("Galby", "000", 24, null, null);
    List<FireAlert> firesAlert = new ArrayList<>();
    firesAlert.add(fireAlert_1);
    firesAlert.add(fireAlert_2);

    when(houseHoldService.getHouseholdByAddress(ADDRESS_1)).thenReturn(household_1);
    when(firestationService.getFirestationsByHousehold(household_1)).thenReturn(firestations);
    when(personService.getPersonsByHousehold(household_1)).thenReturn(persons);
    when(medicalRecordService.getMedicalRecordsByPersons(persons)).thenReturn(medicalRecords);

    urlsService.stationAndPersonsByAddress(ADDRESS_1);

    verify(serialization, times(1)).fireSerialization(firesAlert, "4,1", "stationAndPersonsByAddress", ADDRESS_1);
  }
}