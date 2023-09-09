package com.safetynet.alerts.web.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.dao.FirestationDao;
import com.safetynet.alerts.web.deserialization.model.FirestationDeserialization;
import com.safetynet.alerts.web.logging.EndpointsLogger;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.Household;

/**
 * Some javadoc.
 * 
 * This service class provides operations related to the Firestation entity.
 * 
 * It interacts with the FirestationDao to perform CRUD operations on
 * Firestation objects.
 */
@Service
public class FirestationService {

  private final FirestationDao firestationDao;
  private final HouseHoldService houseHoldService;
  private EndpointsLogger log = new EndpointsLogger();

  @Autowired
  public FirestationService(FirestationDao firestationDao, HouseHoldService houseHoldService) {
    this.firestationDao = firestationDao;
    this.houseHoldService = houseHoldService;
  }

  /**
   * Some javadoc.
   * 
   * Adds a new firestation or updates an existing one with the provided
   * information.
   *
   * @param firestationDeserialization The deserialized firestation data.
   * @param methodeName                The name of the method invoking the
   *                                   operation.
   * @return A ResponseEntity indicating the result of the operation.
   */
  public ResponseEntity<String> addFirestation(FirestationDeserialization firestationDeserialization,
      String methodeName) {
    String station = firestationDeserialization.getStation();
    String address = firestationDeserialization.getAddress();

    Household household = houseHoldService.getHouseholdByAddress(address);
    Firestation firestation = getFirestationByStation(station);

    if (household == null) {
      household = houseHoldService.saveHousehold(address);
    }

    if (firestation == null) {
      firestation = new Firestation();
      firestation.setIdHouseholds(Arrays.asList(household.getId()));
      firestation.setStation(station);
      saveFirestation(firestation);
      return log.addedSuccessfully(methodeName);
    } else if (firestationGetIdHousehold(firestation, household)) {
      return log.ExistingMappingBetweenAddressAndFirestation(methodeName);
    } else {
      firestation.getIdHouseholds().add(household.getId());
      firestationDao.save(firestation);
      return log.addedSuccessfully(methodeName);
    }
  }

  /**
   * Some javadoc.
   * 
   * Updates the station number for a household at the specified address.
   *
   * @param firestationDeserialization The deserialized firestation data.
   * @param address                    The address of the household to update.
   * @param methodeName                The name of the method invoking the
   *                                   operation.
   * @return A ResponseEntity indicating the result of the operation.
   */
  public ResponseEntity<String> updateStationByAddress(FirestationDeserialization firestationDeserialization,
      String address, String methodeName) {
    Household household = houseHoldService.getHouseholdByAddress(address);
    if (household == null) {
      return log.argumentHasNoMatch(methodeName);
    }
    String station = firestationDeserialization.getStation();
    List<Firestation> firestations = getFirestationsByHousehold(household);
    for (Firestation firestation : firestations) {
      if (!firestation.getStation().equals(station)) {
        firestation.getIdHouseholds().remove(Integer.valueOf(household.getId()));
        saveFirestation(firestation);
      }
    }
    Firestation firestation = getFirestationByStation(station);
    if (firestation == null) {
      firestation = new Firestation();
      firestation.setStation(station);
      firestation.setIdHouseholds(Arrays.asList(household.getId()));
      saveFirestation(firestation);
      return log.updatedSuccessfully(methodeName);
    } else {
      ArrayList<Integer> newIdHouseholds = new ArrayList<>();
      newIdHouseholds.add(household.getId());
      firestation.setIdHouseholds(newIdHouseholds);
      saveFirestation(firestation);
      return log.updatedSuccessfully(methodeName);
    }
  }

  /**
   * 
   * Deletes station numbers associated with a specific address.
   *
   * @param address     The address for which to delete station numbers.
   * @param methodeName The name of the method invoking the operation.
   * @return A ResponseEntity indicating the result of the operation.
   */
  public ResponseEntity<String> deleteStationAtThisAddress(String address, String methodeName) {

    // Get all firestations by an address :
    Household household = houseHoldService.getHouseholdByAddress(address);
    List<Firestation> firestations = getFirestationsByHousehold(household);
    // Check if any firestation match with this address :
    if (firestations.isEmpty()) {
      return log.argumentHasNoMatch(methodeName);
    } else {
      // For each firestation check if they have at least one address or delete it :
      for (Firestation firestation : firestations) {
        List<Integer> idHouseholds = firestation.getIdHouseholds();
        idHouseholds.remove(Integer.valueOf(household.getId()));
        if (idHouseholds.isEmpty()) {
          firestationDao.deleteByStation(firestation.getStation());
        } else {
          firestation.setIdHouseholds(idHouseholds);
          saveFirestation(firestation);
        }
      }
      return log.deletedSuccessfully(methodeName);
    }
  }

  /**
   * Some javadoc.
   * 
   * Deletes a firestation object based on its firestation number.
   *
   * @param station     The firestation number to delete.
   * @param methodeName The name of the method invoking the deletion.
   * @return A ResponseEntity indicating the result of the deletion operation.
   */
  public ResponseEntity<String> deleteFirestationByStation(String station, String methodeName) {
    // Check if the firestation exists :
    Firestation firestation = getFirestationByStation(station);
    if (firestation != null) {
      firestationDao.deleteByStation(station);
      return log.deletedSuccessfully(methodeName);
    } else {
      return log.argumentHasNoMatch(methodeName);
    }
  }

  /**
   * Some javadoc.
   * 
   * Get all firestation objects in the system.
   *
   * @return A list of all firestation objects.
   */
  public List<Firestation> getAllFirestations() {
    return firestationDao.findAll();
  }

  /**
   * Some javadoc.
   * 
   * Save a firestation inside the JPA repository.
   *
   * @return A firestation object.
   */
  public Firestation saveFirestation(Firestation firestation) {
    return firestationDao.save(firestation);
  }

  /**
   * Some javadoc.
   * 
   * Get a firestation object by his firestation number.
   *
   * @param station The firestation number to link at this objects.
   */
  public Firestation getFirestationByStation(String station) {
    return firestationDao.findByStation(station);
  }

  /**
   * Some javadoc.
   * 
   * Get a firestation object list by id
   *
   * @param id An list of firestation id.
   */
  public List<Firestation> getFirestationsByIdList(List<Integer> ids) {
    List<Firestation> firestations = new ArrayList<>();
    for (Integer id : ids) {
      Optional<Firestation> firestationOptional = firestationDao.findById(id);
      firestationOptional.ifPresent(firestations::add);
    }
    return firestations;
  }

  /**
   * Some javadoc.
   * 
   * Retrieves a list of firestation objects associated with a specific household.
   *
   * @param household The household for which to retrieve associated firestation
   *                  objects.
   * @return A list of firestation objects associated with the specified
   *         household.
   */
  public List<Firestation> getFirestationsByHousehold(Household household) {
    List<Firestation> allFirestations = getAllFirestations();
    List<Firestation> firestations = new ArrayList<>();
    for (Firestation firestation : allFirestations) {
      for (Integer idhousehold : firestation.getIdHouseholds()) {
        if (idhousehold == household.getId()) {
          firestations.add(firestation);
        }
      }
    }
    return firestations;
  }

  /**
   * Some javadoc.
   * 
   * Checks if a firestation object has an associated household with a given ID.
   *
   * @param firestation The firestation object to check.
   * @param household   The household for which to check association.
   * @return True if the firestation is associated with the specified household,
   *         otherwise False.
   */
  public Boolean firestationGetIdHousehold(Firestation firestation, Household household) {
    for (Integer idHousehold : firestation.getIdHouseholds()) {
      if (idHousehold == household.getId()) {
        return true;
      }
    }
    return false;
  }
}