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

    private String city, station, firstName, lastName;
    private List<Person> persons;
    private String address;
    private List<Person> childrenPerson;
    private List<Person> adultsPerson;
    private List<MedicalRecord> medicalRecords;
    private List<String> addresses;

    @BeforeEach
    public void setUp() {
        firstName = "Quentin";
        lastName = "Beraud";
        station = "1";
        city = "Lyon";
        address = "123 ici";
        addresses = Arrays.asList("123 ici", "123 là");
        persons = new ArrayList<>();
        childrenPerson = new ArrayList<>();
        adultsPerson = new ArrayList<>();
        medicalRecords = new ArrayList<>();
    }

   /**
     * SomeJavadoc.
     * Test for 'GetAllResidentsEmailsWithWrongCity' method.
     */
    @Test
    void testGetAllResidentsEmailsWithWrongCity() {
        when(personService.getPersonsByCity(city)).thenReturn(persons);
        urlsService.allResidentsEmailsFromCity(city);
        verify(serialization, times(1)).emptyAnswer("getAllResidentsEmails", city);
    }

   /**
     * SomeJavadoc.
     * Test for 'GetChildrenByAddress' method with empty list.
     */
    @Test
    void testGetChildrenByAddressIfEmptyList() {
        when(personService.getPersonsListByAddress(address)).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordsByPersons(persons, medicalRecords)).thenReturn(medicalRecords);
        when(personService.getChildren(persons, medicalRecords)).thenReturn(childrenPerson);
        when(personService.getAdults(persons, medicalRecords)).thenReturn(adultsPerson);
        urlsService.childrenLivingAtThisAddress(address);
        verify(serialization, times(1)).emptyAnswer("getChildrenByAddress", address);
    }

    /**
     * SomeJavadoc.
     * Test for 'GetFireStationData' method with empty list.
     */
    @Test
    void testGetFireStationDataIfEmptyPersonsList() {
        List<Integer> minorsThenAdultsNumber = Arrays.asList(2, 8);
        when(firestationService.getAddressesCoveredByStationfireNumber(station)).thenReturn(addresses);
        when(personService.getPersonsByAddresses(addresses)).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordsByPersons(persons, medicalRecords)).thenReturn(medicalRecords);
        when(medicalRecordService.getMinorsAndAdultsNumbers(medicalRecords)).thenReturn(minorsThenAdultsNumber);
        urlsService.personCoveredByFireStation(station);
        verify(serialization, times(1)).emptyAnswer("getFireStationData", station);
    }

   /**
     * SomeJavadoc.
     * Test for 'GetPersonInfoByFirstNameAndLastName' method with wrong First Name.
     */
    @Test
    void testGetPersonInfoByFirstNameAndLastNameWithWrongFirstName() {
        when(personService.getPersonsByFirstNameAndLastName(firstName, lastName)).thenReturn(persons);
        urlsService.personInfoFromFirstAndLastName(firstName, lastName);
        verify(serialization, times(1)).emptyAnswer("getPersonInfoByFirstNameAndLastName", firstName + " " + lastName);
    }

    /**
     * SomeJavadoc.
     * Test for 'GetPersonsWithTheirMedicalRecordsByStationNumber' method with wrong station number.
     */
    @Test
    void testGetPersonsWithTheirMedicalRecordsByStationNumberWithWrongNumber() {
        addresses = new ArrayList<>();
        when(firestationService.getAddressesCoveredByStationfireNumber(station)).thenReturn(addresses);
        urlsService.personsByAddressFromStationNumber(station);
        verify(serialization, times(1)).emptyAnswer("getPersonsWithTheirMedicalRecordsByStationNumber", station);
    }

   /**
     * SomeJavadoc.
     * Test for 'GetPhoneNumbersByFirestationNumber' method with wrong station number.
     */
    @Test
    void testGetPhoneNumbersByFirestationNumberWrongStationNumber() {
        when(firestationService.getAddressesCoveredByStationfireNumber(station)).thenReturn(addresses);
        when(personService.getPersonsByAddresses(addresses)).thenReturn(persons);
        urlsService.phoneNumberOfResidentsCoveredByFireStation(station);
        verify(serialization, times(1)).emptyAnswer("getPhoneNumbersByFirestationNumber", station);
    }

   /**
     * SomeJavadoc.
     * Test for 'GetStationAndPersonsByAddress' method.
     */
    @Test
    void testGetStationAndPersonsByAddress() {
        when(personService.getPersonsListByAddress(address)).thenReturn(persons);
        when(medicalRecordService.getMedicalRecordsByPersons(persons, medicalRecords)).thenReturn(medicalRecords);
        when(firestationService.getStationNumberByAdress(address)).thenReturn("1");
        urlsService.residentsAndFireStationAtThisAddress(address);
        verify(serialization, times(1)).emptyAnswer("getStationAndPersonsByAddress", address);
    }
}
