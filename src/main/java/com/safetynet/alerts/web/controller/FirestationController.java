package com.safetynet.alerts.web.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.web.communUtilts.DataManipulationUtils;
import com.safetynet.alerts.web.deserialization.model.FirestationDeserialization;
import com.safetynet.alerts.web.logging.EndpointsLogger;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.service.FirestationService;

/**
 * Some javadoc.
 * 
 * This class represents a REST controller for managing firestations.
 * It provides endpoints for adding, updating, and deleting firestations,
 * as well as retrieving information about persons covered by a specific
 * firestation.
 */
@RestController
@RequestMapping("/firestation")
public class FirestationController {
  private FirestationService firestationService;
  private DataManipulationUtils beanService = new DataManipulationUtils();
  private EndpointsLogger log = new EndpointsLogger();

  public FirestationController(FirestationService firestationService) {
    this.firestationService = firestationService;
  }

  /**
   * Some javadoc.
   * 
   * Adds a new firestation to the system.
   *
   * @param firestationDeserialization The Firestation object representing
   *                                   the new firestation to be added in the
   *                                   deserialization format.
   */
  @PostMapping("")
  public ResponseEntity<String> addFirestation(@RequestBody FirestationDeserialization firestationDeserialization) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);

    // Check the request content :
    Boolean fieldsAreNull = beanService.areFieldsNullExceptId(firestationDeserialization);
    if (fieldsAreNull) {
      return log.incorrectContent(methodeName);
    } else {
      return firestationService.addFirestation(firestationDeserialization, methodeName);
    }
  }

  /**
   * Some javadoc.
   * 
   * Updates the firestation number for a specific address.
   *
   * @param address                    The address of the firestation to be
   *                                   updated.
   * @param firestationDeserialization The updated Firestation object in the
   *                                   deserialization format.
   */
  @PutMapping("/{address}")
  public ResponseEntity<String> updateStationByAddress(@PathVariable("address") String address,
      @RequestBody FirestationDeserialization firestationDeserialization) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName, address);

    // Check the request content :
    Boolean fieldsAreNull = beanService.areFieldsNullExceptId(firestationDeserialization);
    if (fieldsAreNull) {
      return log.incorrectContent(methodeName);
    } else {
      return firestationService.updateStationByAddress(firestationDeserialization, address, methodeName);
    }
  }

  /**
   * Some javadoc.
   * 
   * Deletes a firestation based on address.
   *
   * @param address The address of the firestation to be deleted.
   */
  @DeleteMapping("/address/{address}")
  public ResponseEntity<String> deleteStationAtThisAddress(@PathVariable("address") String address) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);
    return firestationService.deleteStationAtThisAddress(address, methodeName);
  }

  /**
   * Some javadoc.
   * 
   * Deletes a firestation based on this station.
   *
   * @param station The station number of the firestation to be deleted.
   */
  @DeleteMapping("/station/{station}")
  public ResponseEntity<String> deleteFirestationByStation(@PathVariable("station") String station) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);
    return firestationService.deleteFirestationByStation(station, methodeName);
  }

  /**
   * Some javadoc.
   * 
   * Get all firestations.
   */
  @GetMapping("/all")
  public List<Firestation> getAllFirestations() {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);
    return firestationService.getAllFirestations();
  }
}