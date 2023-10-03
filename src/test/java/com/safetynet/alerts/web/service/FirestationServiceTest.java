package com.safetynet.alerts.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.safetynet.alerts.web.deserialization.model.FirestationDeserialization;
import com.safetynet.alerts.web.httpResponse.ResponseBuilder;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.Household;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {
  @Autowired
  @InjectMocks
  private FirestationService firestationService;

  @Mock
  private HouseHoldService houseHoldService;

  @Mock
  private ResponseBuilder response = new ResponseBuilder();

  // Example of addresses or stations :
  private String ADDRESS_1, ADDRESS_2, ADDRESS_3, STATION_1, STATION_2;

  // Example of JSON entries :
  private List<FirestationDeserialization> firestationDeserializations;
  private FirestationDeserialization firestationDeserialization_1, firestationDeserialization_2;

  // Example of converted data :
  private List<Household> households;
  private Household household_1, household_2, household_3;
  private List<Integer> idHouseholds_firestation_1, idHouseholds_firestation_2;
  private List<Firestation> firestations;
  private Firestation firestation_1, firestation_2;

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

    // Set Households example data corresponding :
    households = new ArrayList<>();
    household_1 = new Household(1, ADDRESS_1);
    household_2 = new Household(2, ADDRESS_2);
    household_3 = new Household(3, ADDRESS_3);
    households.add(household_1);
    households.add(household_2);
    households.add(household_3);

    // Set id of households corresponding :
    idHouseholds_firestation_1 = new ArrayList<>();
    idHouseholds_firestation_1.add(household_1.getId());

    idHouseholds_firestation_2 = new ArrayList<>();
    idHouseholds_firestation_2.add(household_2.getId());
    idHouseholds_firestation_2.add(household_3.getId());

    // Set Firestation example data corresponding
    firestations = new ArrayList<>();
    firestation_1 = new Firestation(1, idHouseholds_firestation_1, STATION_1);
    firestation_2 = new Firestation(2, idHouseholds_firestation_2, STATION_2);
    firestations.add(firestation_1);
    firestations.add(firestation_2);
    firestationService.setFirestations(firestations);
  }

  @Test
    void testAddFirestationWithSucces() {
        when(houseHoldService.getHouseholdByAddress(ADDRESS_1)).thenReturn(household_1);

        firestationService.addFirestation(firestationDeserialization_1, "addFirestation");
    }

  @Test
    void testAddFirestationWithExistingMapping() {
      when(houseHoldService.getHouseholdByAddress(ADDRESS_1)).thenReturn(household_1);

      ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.CONFLICT).body("Mapping already exist.");
      ResponseEntity<String> result = firestationService.addFirestation(firestationDeserialization_1, "addFirestation");

      assertEquals(expected, result);
    }

  @Test
    void testAddFirestationWithExistentHousehold() {
      when(houseHoldService.getHouseholdByAddress(ADDRESS_2)).thenReturn(null);
      when(houseHoldService.saveHousehold(ADDRESS_2)).thenReturn(household_1);

      ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.CREATED).body("Content added successfully.");
      ResponseEntity<String> result = firestationService.addFirestation(firestationDeserialization_2, "addFirestation");

      assertEquals(expected, result);
    }

  @Test
    void testUpdateStationByAddressWithInexistentHousehold() {
      when(houseHoldService.getHouseholdByAddress(ADDRESS_1)).thenReturn(null);

      ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.NOT_FOUND).body("Argument has no match.");
      ResponseEntity<String> result = firestationService.updateStationByAddress(firestationDeserialization_1, ADDRESS_1, "updateStationByAddress");

      assertEquals(expected, result);
    }

  @Test
    void testUpdateStationByAddressUpdatedSuccessfully() {
      when(houseHoldService.getHouseholdByAddress(ADDRESS_1)).thenReturn(household_1);

      ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.OK).body("Content updated successfully.");
      ResponseEntity<String> result = firestationService.updateStationByAddress(firestationDeserialization_1, ADDRESS_1, "updateStationByAddress");

      assertEquals(expected, result);
    }

  @Test
    void testUpdateStationByAddressUpdatedSuccessfullyWithInexistentFirestation() {
      when(houseHoldService.getHouseholdByAddress(ADDRESS_1)).thenReturn(household_1);

      ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.OK).body("Content updated successfully.");
      ResponseEntity<String> result = firestationService.updateStationByAddress(firestationDeserialization_1, ADDRESS_1, "updateStationByAddress");

      assertEquals(expected, result);
    }

  @Test
    void testDeleteFirestationByStationSuccessfully() {

      ResponseEntity<String> expected = ResponseEntity.status(HttpStatus.OK).body("Content deleted successfully.");
      ResponseEntity<String> result = firestationService.deleteFirestationByStation(STATION_1,
                "deleteFirestationByStation");

      assertEquals(expected, result);
    }

  @Test
    void testDeleteStationAtThisAddress() {
      when(houseHoldService.getHouseholdByAddress(ADDRESS_1)).thenReturn(household_1);

      firestationService.deleteStationAtThisAddress(ADDRESS_1,
                "deleteStationNumbersAtThisAddress");

  }
}