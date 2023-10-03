package com.safetynet.alerts.web.integration;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.web.controller.FirestationController;
import com.safetynet.alerts.web.deserialization.model.FirestationDeserialization;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.service.FirestationService;
import com.safetynet.alerts.web.service.HouseHoldService;

@SpringBootTest
public class FirestationControllerIT {

  private MockMvc mvc;

  @Autowired
  FirestationService firestationService;

  @Autowired
  HouseHoldService houseHoldService;

  String address = "Rue du marché";
  String station = "88";
  String updateStation = "90";

  @BeforeEach
  public void setup() {
    this.mvc = MockMvcBuilders
        .standaloneSetup(new FirestationController(firestationService))
        .setControllerAdvice()
        .build();

    Firestation firestation = firestationService.getFirestationByStation(station);
    if (firestation != null) {
      firestationService.deleteFirestationByStation(station, address);
    }
  }

  @Test
  public void getAllFirestationTest() throws Exception {
    mvc.perform(MockMvcRequestBuilders.get("/firestation/all"))
        .andExpect(status().isOk());
  }

  @Test
  public void addFirestationTest() throws Exception {
    // New firestation added
    FirestationDeserialization newFirestation = new FirestationDeserialization();
    newFirestation.setStation(station);
    newFirestation.setAddress(address);

    // Firestation on JSON
    ObjectMapper objectMapper = new ObjectMapper();
    String newFirestationJson = objectMapper.writeValueAsString(newFirestation);

    // Send POST request to add the firestation
    mvc.perform(MockMvcRequestBuilders.post("/firestation")
        .contentType(MediaType.APPLICATION_JSON)
        .content(newFirestationJson))
        .andExpect(status().isCreated());

    // Check if the firestation exists and match the id household
    List<Firestation> allFirestations = firestationService.getAllFirestations();
    Household matchingHousehold = houseHoldService.getHouseholdByAddress(address);
    boolean firestationAdded = false;
    for (Firestation firestation : allFirestations) {
      if (station.equals(firestation.getStation())
          & firestation.getIdHouseholds().contains(matchingHousehold.getId())) {
        firestationAdded = true;
        break;
      }
    }

    // Check the successfully added firestation
    assertTrue(firestationAdded);
  }

  @Test
  public void deleteFirestationByStationTest() throws Exception {
    // Add Firestation
    addFirestationTest();

    // Send DELETE request to delete by station
    mvc.perform(MockMvcRequestBuilders.delete("/firestation/station/{station}", station))
        .andExpect(status().isOk());

    // Checks if the firestation has deleted
    List<Firestation> allFirestations = firestationService.getAllFirestations();
    boolean firestationDeleted = true;
    for (Firestation firestation : allFirestations) {
      if (station.equals(firestation.getStation())) {
        firestationDeleted = false;
        break;
      }
    }

    // Check the successfully deleted firestation
    assertTrue(firestationDeleted);
  }

  @Test
  public void deleteFirestationByAddressTest() throws Exception {
    // Add Firestation
    addFirestationTest();

    // Send DELETE request by address
    mvc.perform(MockMvcRequestBuilders.delete("/firestation/address/{address}", address))
        .andExpect(status().isOk());

    // Checks if the firestation has been deleted
    List<Firestation> allFirestations = firestationService.getAllFirestations();
    Household matchingHousehold = houseHoldService.getHouseholdByAddress(address);
    boolean firestationDeleted = true;
    for (Firestation firestation : allFirestations) {
      if (station.equals(firestation.getStation())
          & firestation.getIdHouseholds().contains(matchingHousehold.getId())) {
        firestationDeleted = false;
        break;
      }
    }

    // Check the successfully deleted firestation
    assertTrue(firestationDeleted);
  }

  @Test
  public void updateStationByAddressTest() throws Exception {
    // Add a new Firestation
    addFirestationTest();

    // New firestation updated
    FirestationDeserialization updateFirestation = new FirestationDeserialization();
    updateFirestation.setStation(updateStation);
    updateFirestation.setAddress(address);

    // Firestation on JSON
    ObjectMapper objectMapper = new ObjectMapper();
    String updatedFirestationJson = objectMapper.writeValueAsString(updateFirestation);

    // Send PUT request to update a firestation
    mvc.perform(MockMvcRequestBuilders.put("/firestation/{address}", address)
        .contentType(MediaType.APPLICATION_JSON)
        .content(updatedFirestationJson))
        .andExpect(status().isOk());

    // Checks if the firestation has updated
    List<Firestation> allFirestations = firestationService.getAllFirestations();
    Household matchingHousehold = houseHoldService.getHouseholdByAddress(address);
    boolean firestationUpdated = false;
    for (Firestation firestation : allFirestations) {
      if (updateStation.equals(firestation.getStation())
          & firestation.getIdHouseholds().contains(matchingHousehold.getId())) {
        firestationUpdated = true;
        break;
      }
    }

    // Check the successfully added firestation
    assertTrue(firestationUpdated);
  }
}