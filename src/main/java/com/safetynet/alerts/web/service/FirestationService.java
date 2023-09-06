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
 * 
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
            return log.mappingBetwenAddressAndFirestationExits(methodeName);
        } else {
            firestation.getIdHouseholds().add(household.getId());
            firestationDao.save(firestation);
            return log.addedSuccessfully(methodeName);
        }
    }

    public ResponseEntity<String> updateStationNumberByAddress(FirestationDeserialization firestationDeserialization,
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

    public ResponseEntity<String> deleteStationNumbersAtThisAddress(String address, String methodeName) {

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
                    deleteFirestationByStation(firestation.getStation());
                } else {
                    firestation.setIdHouseholds(idHouseholds);
                    saveFirestation(firestation);
                }
            }
            return log.deletedSuccessfully(methodeName);
        }
    }

    public ResponseEntity<String> deleteFirestationByStation(String station, String methodeName) {
        // Check if the firestation exists :
        Firestation firestation = getFirestationByStation(station);
        if (firestation != null) {
            deleteFirestationByStation(station);
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

    public Firestation saveFirestation(Firestation firestation) {
        return firestationDao.save(firestation);
    }

    /**
     * Some javadoc.
     * 
     * Deletes a firestation object based on his firestation number.
     *
     * @param station The firestation number to delete.
     */
    public void deleteFirestationByStation(String station) {
        firestationDao.deleteByStation(station);
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

    public Boolean firestationGetIdHousehold(Firestation firestation, Household household) {
        for (Integer idHousehold : firestation.getIdHouseholds()) {
            if (idHousehold == household.getId()) {
                return true;
            }
        }
        return false;
    }
}