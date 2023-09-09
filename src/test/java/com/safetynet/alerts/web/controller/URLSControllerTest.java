package com.safetynet.alerts.web.controller;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

  @Test
  void testGetAllResidentsEmails() {
    String city = "Lyon";
    urlsController.getAllResidentsEmails(city);
    verify(urlsService, times(1)).allResidentsEmailsFromCity(city);
  }

  @Test
  void testGetChildrenLivingAtThisAddress() {
    String address = "Rue de 123";
    urlsController.getChildrenLivingAtThisAddress(address);
    verify(urlsService, times(1)).childrenLivingAtThisAddress(address);
  }

  @Test
  void testGetPersonCoveredByFirestation() {
    String station = "1";
    urlsController.getPersonCoveredByFirestation(station);
    verify(urlsService, times(1)).personCoveredByFireStation(station);
  }

  @Test
  void testGetPersonInfoByFirstAndLastName() {
    String firstName = "Quentin";
    String lastName = "BOB";
    urlsController.getPersonInfoByFirstAndLastName(firstName, lastName);
    verify(urlsService, times(1)).personInfoByFirstAndLastName(firstName, lastName);
  }

  @Test
  void testGetPersonsByHouseholdsFromStration() {
    String stationNumber = "1";
    urlsController.getPersonsByHouseholdsFromStration(stationNumber);
    verify(urlsService, times(1)).personsByHouseholdsFromStation(stationNumber);
  }

  @Test
  void testGetPersonsPhoneNumbersCoveredByStation() {
    String stationNumber = "1";
    urlsController.getPersonsPhoneNumbersCoveredByStation(stationNumber);
    verify(urlsService, times(1)).personsPhoneNumbersCoveredByStation(stationNumber);
  }

  @Test
  void testGetStationAndPersonsByAddress() {
    String address = "Rue de 123";
    urlsController.getStationAndPersonsByAddress(address);
    verify(urlsService, times(1)).stationAndPersonsByAddress(address);
  }
}