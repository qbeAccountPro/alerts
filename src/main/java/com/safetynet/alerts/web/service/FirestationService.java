package com.safetynet.alerts.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.dao.FirestationDao;
import com.safetynet.alerts.web.model.Firestation;

/**
 * Some javadoc.
 * This service class provides operations related to the Firestation entity.
 * It interacts with the FirestationDao to perform CRUD operations on
 * Firestation objects.
 */
@Service
public class FirestationService {

    private final FirestationDao firestationDao;
    @Autowired
    public FirestationService(FirestationDao firestationDao) {
        this.firestationDao = firestationDao;
    }

    /**
     * Some javadoc.
     * Retrieves a list of all firestations in the system.
     *
     * @return A list of all Firestation objects.
     */
    public List<Firestation> getAllFirestations() {
        return firestationDao.findAll();
    }

    /**
     * Some javadoc.
     * Retrieves a list of addresses covered by a specific firestation number.
     *
     * @param firestationNumber The number of the firestation for which to retrieve
     *                          the addresses.
     * @return A list of addresses covered by the specified firestation number.
     */
    public List<String> getAddressesCoveredByStationfireNumber(String firestationNumber) {
        List<Firestation> firestations = getAllFirestations();
        List<String> addresses = new ArrayList<>();
        for (Firestation firestation : firestations) {
            if (firestation.getStation().equals(firestationNumber)) {
                addresses.add(firestation.getAddress());
            }
        }
        return addresses;
    }

    /**
     * Some javadoc.
     * Saves a new or existing Firestation object.
     *
     * @param firestation The Firestation object to save.
     */
    public void saveFirestation(Firestation firestation) {
        firestationDao.save(firestation);
    }

    /**
     * Some javadoc.
     * Finds a Firestation object based on its address.
     *
     * @param address The address of the firestation to find.
     * @return The Firestation object corresponding to the provided address, or null
     *         if not found.
     */
    public Firestation findFirestationByAddress(String address) {
        return firestationDao.findByAddress(address);
    }

    /**
     * Some javadoc.
     * Deletes a Firestation object based on its address.
     *
     * @param address The address of the firestation to delete.
     */
    public void deleteFirestationByAddress(String address) {
        firestationDao.deleteByAddress(address);
    }

    /**
     * Some javadoc.
     * Deletes a list of Firestation objects based on their firestation number.
     *
     * @param station The firestation number of the firestations to delete.
     */
    public void deleteFirestationByStation(String station) {
        firestationDao.deleteByStation(station);
    }

    public String getStationNumberByAdress(String address) {
        List<Firestation> firestations = getAllFirestations();
        for (Firestation firestation : firestations) {
            if (firestation.getAddress().equals(address)) {
                return firestation.getStation();
            }
        }
        return null;
    }
}
