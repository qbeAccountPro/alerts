package com.safetynet.alerts.web.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.safetynet.alerts.web.deserialization.model.FirestationDeserialization;
import com.safetynet.alerts.web.logging.EndpointsLogger;
import com.safetynet.alerts.web.service.FirestationService;

@ExtendWith(MockitoExtension.class)
public class FirestationControllerTest {

  @Mock
  private FirestationService firestationService;

  @Mock
  private EndpointsLogger log;

  @InjectMocks
  private FirestationController firestationController;

  private FirestationDeserialization firestation_1;
  private String address, station;

  @BeforeEach
  public void setUp() {
    station = "1";
    address = "123 Main St";
    firestation_1 = new FirestationDeserialization(1, address, "Station_1");
  }

  @Test
  void testAddFirestation() {
    firestationController.addFirestation(firestation_1);
    verify(firestationService, times(1)).addFirestation(firestation_1, "addFirestation");
  }

  @Test
  void testAddFirestationWithNullProperty() {
    firestation_1.setAddress(null);
    ResponseEntity<String> result = firestationController.addFirestation(firestation_1);
    ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect content.");
    assertEquals(expected, result);
    verify(firestationService, times(0)).addFirestation(firestation_1, "addFirestation");
  }

  @Test
  void testDeleteFirestationByStation() {
    firestationController.deleteFirestationByStation(station);
    verify(firestationService, times(1)).deleteFirestationByStation(station, "deleteFirestationByStation");
  }

  @Test
  void testDeleteStationAtThisAddress() {
    firestationController.deleteStationAtThisAddress(address);
    verify(firestationService, times(1)).deleteStationAtThisAddress(address, "deleteStationAtThisAddress");
  }

  @Test
  void testGetAllFirestations() {
    firestationController.getAllFirestations();
    verify(firestationService, times(1)).getAllFirestations();
  }

  @Test
  void testUpdateStationByAddress() {
    firestationController.updateStationByAddress(address, firestation_1);
    verify(firestationService, times(1)).updateStationByAddress(firestation_1, address, "updateStationByAddress");
  }

  @Test
  void testUpdateStationByAddressWithNullProperty() {
    firestation_1.setAddress(null);
    ResponseEntity<String> result = firestationController.updateStationByAddress(address, firestation_1);
    ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect content.");
    assertEquals(expected, result);
    verify(firestationService, times(0)).updateStationByAddress(firestation_1, address, "updateStationByAddress");
  }
}