package com.safetynet.alerts.web.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.web.service.URLSService;

@ExtendWith(MockitoExtension.class)
public class URLSControllerTest {
    @Mock
    private URLSService urlsService;

    @InjectMocks
    private URLSController urlsController;

    /**
     * SomeJavadoc.
     * Test case for retrieving fire station data by station number.
     */
    @Test
    void testGetFireStationData() {
        String station = "1";
        urlsController.getFireStationData(station);
        verify(urlsService, times(1)).personCoveredByFireStation(station);
    }

    /**
     * SomeJavadoc.
     * Test case for retrieving children by address.
     */
    @Test
    void testGetChildrenByAddress() {
        String address = "Rue de 123";
        urlsController.getChildrenByAddress(address);
        verify(urlsService, times(1)).childrenLivingAtThisAddress(address);
    }

    /**
     * SomeJavadoc.
     * Test case for retrieving all residents' emails by city.
     */
    @Test
    void testGetAllResidentsEmails() {
        String city = "Lyon";
        urlsController.getAllResidentsEmails(city);
        verify(urlsService, times(1)).allResidentsEmailsFromCity(city);
    }

    /**
     * SomeJavadoc.
     * Test case for retrieving persons with their medical records by station
     * number.
     */
    @Test
    void testGetPersonInfoByFirstNameAndLastName() {
        String firstName = "Quentin";
        String lastName = "BOB";
        urlsController.getPersonInfoByFirstNameAndLastName(firstName, lastName);
        verify(urlsService, times(1)).personInfoFromFirstAndLastName(firstName, lastName);
    }

    /**
     * SomeJavadoc.
     * Test case for retrieving persons with their medical records by station
     * number.
     */
    @Test
    void testGetPersonsWithTheirMedicalRecordsByStationNumber() {
        String stationNumber = "1";
        urlsController.getPersonsWithTheirMedicalRecordsByStationNumber(stationNumber);
        verify(urlsService, times(1)).personsByAddressFromStationNumber(stationNumber);
    }

    /**
     * SomeJavadoc.
     * Test case for retrieving phone numbers by fire station number.
     */
    @Test
    void testGetPhoneNumbersByFirestationNumber() {
        String stationNumber = "1";
        urlsController.getPhoneNumbersByFirestationNumber(stationNumber);
        verify(urlsService, times(1)).phoneNumberOfResidentsCoveredByFireStation(stationNumber);
    }

    /**
     * SomeJavadoc.
     * Test case for retrieving station and persons by address.
     */
    @Test
    void testGetStationAndPersonsByAddress() {
        String address = "Rue de 123";
        urlsController.getStationAndPersonsByAddress(address);
        verify(urlsService, times(1)).residentsAndFireStationAtThisAddress(address);
    }
}
