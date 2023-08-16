package com.safetynet.alerts.web.controller;

import org.tinylog.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.service.BeanService;
import com.safetynet.alerts.web.service.FirestationService;

/**
 * Some javadoc.
 * This class represents a REST controller for managing firestations.
 * It provides endpoints for adding, updating, and deleting firestations,
 * as well as retrieving information about persons covered by a specific
 * firestation.
 */
@RestController
@RequestMapping("/firestation")
public class FirestationController {

    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    /**
     * Some javadoc.
     * Adds a new firestation to the system.
     *
     * @param firestation The Firestation object representing the new firestation to
     *                    be added.
     */
    @PostMapping("")
    public void addFirestation(@RequestBody Firestation firestation) {
        firestationService.saveFirestation(firestation);
        Logger.info("Request : " + BeanService.getCurrentMethodName() + ".");
    }

    /**
     * Some javadoc.
     * Updates the firestation number for a specific address.
     *
     * @param address        The address of the firestation to be updated.
     * @param newFirestation The updated Firestation object with the new firestation
     *                       number.
     */
    @PutMapping("/{address}")
    public void updateNumberStationFromAddress(@PathVariable("address") String address,
            @RequestBody Firestation newFirestation) {
        Firestation oldFirestation = firestationService.findFirestationByAddress(address);
        if (oldFirestation != null) {
            try {
                Firestation updateFirestation = BeanService.updateBeanWithNotNullPropertiesFromNewObject(oldFirestation,
                        newFirestation);
                updateFirestation.setId(oldFirestation.getId());
                updateFirestation.setAddress(oldFirestation.getAddress());
                firestationService.saveFirestation(updateFirestation);
                Logger.info(
                        "Request : " + BeanService.getCurrentMethodName() + " with this address : " + address + ".");
            } catch (Exception e) {
                Logger.error("Request : " + BeanService.getCurrentMethodName() + " with this address : " + address
                        + ", genereted this exception : " + e);
            }
        } else {
            Logger.error("Request : " + BeanService.getCurrentMethodName() + " with this address : " + address
                    + ", doesn't match with any Firestation.");
        }
    }

    /**
     * Some javadoc.
     * Deletes a firestation based on its address.
     *
     * @param address The address of the firestation to be deleted.
     */
    @Transactional
    @DeleteMapping("/address/{address}")
    public void deleteFirestationByAddress(@PathVariable("address") String address) {
        firestationService.deleteFirestationByAddress(address);
        Logger.info("Request : " + BeanService.getCurrentMethodName() + " with this address : " + address + ".");
    }

    /**
     * Some javadoc.
     * Deletes a firestation based on its station number.
     *
     * @param station The station number of the firestation to be deleted.
     */
    @Transactional
    @DeleteMapping("/station/{station}")
    public void deleteFirestationByStation(@PathVariable("station") String station) {
        firestationService.deleteFirestationByStation(station);
        Logger.info("Request : " + BeanService.getCurrentMethodName() + " with this station : " + station + ".");
    }
}
