package com.safetynet.alerts.web.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.Serialization;
import com.safetynet.alerts.web.serialization.model.ChildAlert;
import com.safetynet.alerts.web.serialization.service.ChildAlertService;
import com.safetynet.alerts.web.serialization.service.FireService;
import com.safetynet.alerts.web.serialization.service.FloodAddressService;
import com.safetynet.alerts.web.serialization.service.FloodService;
import com.safetynet.alerts.web.serialization.service.PersonInfoService;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class URLSServiceTest {

    @Mock
    private FirestationService firestationService;

    @Mock
    private PersonService personService;

    @Mock
    private MedicalRecordService medicalRecordService;

    @Mock
    private ChildAlertService childAlertService;

    @Mock
    private Serialization serialization;

    @Mock
    private FireService fireService;

    @Mock
    private FloodService floodService;

    @Mock
    private FloodAddressService floodAddressService;

    @Mock
    private PersonInfoService personInfoService;

    @InjectMocks
    private URLSService urlsService;

    private String city, station, firstName, lastName, address1, address2;
    private Person person1, person2;
    private MedicalRecord medicalRecord1, medicalRecord2;
    private List<Person> emptyPersonList, persons;
    private List<MedicalRecord> emptyMedicalRecords, medicalRecords;
    private List<String> addresses, allergies, medication;
    private List<Integer> minorsThenAdultsNumber;

    @BeforeEach
    public void setUp() {
        firstName = "Quentin";
        lastName = "Beraud";
        station = "1";
        city = "Lyon";
        allergies = new ArrayList<>();
        medication = new ArrayList<>();
        emptyPersonList = new ArrayList<>();
        emptyMedicalRecords = new ArrayList<>();
        address1 = "123 ici";
        address2 = "123 là";

        person1 = new Person(1, firstName, lastName, address1, city, "69006", "06 88 88 88 88",
                "Quentin.beraud@gmail.com");
        person2 = new Person(2, "George", "Boo", address2, city, "69006", "06 66 66 68 88",
                "george.boo@email.com");

        medicalRecord1 = new MedicalRecord(1, firstName, lastName, "02/25/1988", allergies, medication);
        medicalRecord2 = new MedicalRecord(1, person2.getFirstName(), person2.getLastName(), "08/08/2000", allergies,
                medication);

        minorsThenAdultsNumber = Arrays.asList(2, 8);
        addresses = Arrays.asList(address1, address2);
        persons = Arrays.asList(person1, person2);
        medicalRecords = Arrays.asList(medicalRecord1, medicalRecord2);

    }

   /**
     * SomeJavadoc.
     * Test for 'GetAllResidentsEmailsWithWrongCity' method.
     */
    @Test
    void testAllResidentsEmailsWithWrongCity() {
        when(personService.getPersonsByCity(city)).thenReturn(emptyPersonList);
        urlsService.allResidentsEmailsFromCity(city);
        verify(serialization, times(1)).emptyAnswer("allResidentsEmailsFromCity", city);
    }

   /**
     * SomeJavadoc.
     * Test for 'childrenLivingAtThisAddress' method with empty list.
     */
    @Test
    void testGetChildrenByAddressIfEmptyList() {
        when(personService.getPersonsListByAddress(address1)).thenReturn(emptyPersonList);
        when(medicalRecordService.getMedicalRecordsByPersons(emptyPersonList, emptyMedicalRecords)).thenReturn(emptyMedicalRecords);
        when(personService.getChildren(emptyPersonList, emptyMedicalRecords)).thenReturn(emptyPersonList);
        when(personService.getAdults(emptyPersonList, emptyMedicalRecords)).thenReturn(emptyPersonList);
        urlsService.childrenLivingAtThisAddress(address1);
        verify(serialization, times(1)).emptyAnswer("childrenLivingAtThisAddress", address1);
    }

    /**
     * SomeJavadoc.
     * Test for 'childrenLivingAtThisAddress' method with normal content.
     */
    @Test
    void testchildrenLivingAtThisAddress() {
        List<Person> personLivingAtThisAddress = Arrays.asList(person1);
        List<MedicalRecord> medicalRecordsAtThisAddress = Arrays.asList(medicalRecord1);
        List<ChildAlert> minorsChildAlert = new ArrayList<>();
        ChildAlert childAlert1 = new ChildAlert(firstName, lastName, 35);
        List<ChildAlert> adultsChildAlert = Arrays.asList(childAlert1);

        when(personService.getPersonsListByAddress(address1)).thenReturn(personLivingAtThisAddress);
        when(medicalRecordService.getAllMedicalRecord()).thenReturn(medicalRecords);
        when(medicalRecordService.getMedicalRecordsByPersons(personLivingAtThisAddress, medicalRecords)).thenReturn(medicalRecordsAtThisAddress);
        when(personService.getChildren(personLivingAtThisAddress, medicalRecordsAtThisAddress)).thenReturn(emptyPersonList);
        when(personService.getAdults(personLivingAtThisAddress, medicalRecordsAtThisAddress)).thenReturn(personLivingAtThisAddress);
        urlsService.childrenLivingAtThisAddress(address1);

        verify(serialization, times(1)).childAlertSerialization(minorsChildAlert, adultsChildAlert,
                "childrenLivingAtThisAddress", address1);
    }

    /**
     * SomeJavadoc.
     * Test for 'personCoveredByFireStation' method with empty list.
     */
    @Test
    void testpersonCoveredByFireStationIfEmptyPersonsList() {
        when(firestationService.getAddressesCoveredByStationfireNumber(station)).thenReturn(addresses);
        when(personService.getPersonsByAddresses(addresses)).thenReturn(emptyPersonList);
        when(medicalRecordService.getMedicalRecordsByPersons(emptyPersonList, emptyMedicalRecords))
                .thenReturn(emptyMedicalRecords);
        when(medicalRecordService.getMinorsAndAdultsNumbers(emptyMedicalRecords)).thenReturn(minorsThenAdultsNumber);
        urlsService.personCoveredByFireStation(station);
        verify(serialization, times(1)).emptyAnswer("personCoveredByFireStation", station);
    }

    /**
     * SomeJavadoc.
     * Test for 'personCoveredByFireStation' with content.
     */
    @Test
    void testpersonCoveredByFireStation() {
        when(firestationService.getAddressesCoveredByStationfireNumber(station)).thenReturn(addresses);
        when(personService.getPersonsByAddresses(addresses)).thenReturn(persons);
        when(medicalRecordService.getAllMedicalRecord()).thenReturn(medicalRecords);
        when(medicalRecordService.getMedicalRecordsByPersons(persons,
        medicalRecords)).thenReturn(medicalRecords);
        when(medicalRecordService.getMinorsAndAdultsNumbers(medicalRecords)).thenReturn(minorsThenAdultsNumber);
        urlsService.personCoveredByFireStation(station);
        verify(serialization, times(1)).firestationSerialization(persons, "personCoveredByFireStation",
        station, minorsThenAdultsNumber.get(0), minorsThenAdultsNumber.get(1));
    }

   /**
     * SomeJavadoc.
     * Test for 'GetPersonInfoByFirstNameAndLastName' method with wrong First Name.
     */
    @Test
    void testGetPersonInfoByFirstNameAndLastNameWithWrongFirstName() {
        when(personService.getPersonsByFirstNameAndLastName(firstName, lastName)).thenReturn(emptyPersonList);
        urlsService.personInfoFromFirstAndLastName(firstName, lastName);
        verify(serialization, times(1)).emptyAnswer("personInfoFromFirstAndLastName", firstName + " " + lastName);
    }

    /**
     * SomeJavadoc.
     * Test for 'GetPersonsWithTheirMedicalRecordsByStationNumber' method with wrong
     * station number.
     */
    @Test
    void testGetPersonsWithTheirMedicalRecordsByStationNumberWithWrongNumber() {
        addresses = new ArrayList<>();
        when(firestationService.getAddressesCoveredByStationfireNumber(station)).thenReturn(addresses);
        urlsService.personsByAddressFromStationNumber(station);
        verify(serialization, times(1)).emptyAnswer("personsByAddressFromStationNumber", station);
    }

   /**
     * SomeJavadoc.
     * Test for 'GetPhoneNumbersByFirestationNumber' method with wrong station number.
     */
    @Test
    void testGetPhoneNumbersByFirestationNumberWrongStationNumber() {
        when(firestationService.getAddressesCoveredByStationfireNumber(station)).thenReturn(addresses);
        when(personService.getPersonsByAddresses(addresses)).thenReturn(emptyPersonList);
        urlsService.phoneNumberOfResidentsCoveredByFireStation(station);
        verify(serialization, times(1)).emptyAnswer("phoneNumberOfResidentsCoveredByFireStation", station);
    }

   /**
     * SomeJavadoc.
     * Test for 'GetStationAndPersonsByAddress' method.
     */
    @Test
    void testGetStationAndPersonsByAddress() {
        when(personService.getPersonsListByAddress(address1)).thenReturn(emptyPersonList);
        when(medicalRecordService.getMedicalRecordsByPersons(emptyPersonList, emptyMedicalRecords)).thenReturn(emptyMedicalRecords);
        when(firestationService.getStationNumberByAdress(address1)).thenReturn("1");
        urlsService.residentsAndFireStationAtThisAddress(address1);
        verify(serialization, times(1)).emptyAnswer("residentsAndFireStationAtThisAddress", address1);
    }

}
