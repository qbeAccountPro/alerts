package com.safetynet.alerts.web.controller;

import org.tinylog.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private BeanService beanService = new BeanService();

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
    public ResponseEntity<String> addFirestation(@RequestBody Firestation firestation) {
        Logger.info("Request " + BeanService.getCurrentMethodName() + ".");
        Boolean areFieldsAreNull = beanService.areFieldsNullExceptId(firestation);
        if (areFieldsAreNull) {
            Logger.error("Answer " + BeanService.getCurrentMethodName() + " : content is incorrect.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Firestation content is incorrect.");
        } else {
            firestationService.saveFirestation(firestation);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : firestation added successfully.");
            return ResponseEntity.status(HttpStatus.CREATED).body("Firestation added successfully.");
        }
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
    public ResponseEntity<String> updateNumberStationFromAddress(@PathVariable("address") String address,
            @RequestBody Firestation newFirestation) {
        Logger.info("Request " + BeanService.getCurrentMethodName()  + " : with this address = " + address + ".");
        Boolean areFieldsAreNull = beanService.areFieldsNullExceptId(newFirestation);
        if (areFieldsAreNull) {
            Logger.error("Answer " + BeanService.getCurrentMethodName() + " : content is incorrect.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Firestation content is incorrect.");
        } else {
            Firestation oldFirestation = firestationService.findFirestationByAddress(address);
            if (oldFirestation != null) {
                try {
                    Firestation updateFirestation = BeanService.updateBeanWithNotNullPropertiesFromNewObject(
                            oldFirestation,
                            newFirestation);
                    updateFirestation.setId(oldFirestation.getId());
                    updateFirestation.setAddress(oldFirestation.getAddress());
                    firestationService.saveFirestation(updateFirestation);
                    Logger.info(
                            "Answer " + BeanService.getCurrentMethodName() + " : modified successfully.");
                    return ResponseEntity.status(HttpStatus.OK).body("Firestation modified successfully.");

                } catch (Exception e) {
                    Logger.error("Answer " + BeanService.getCurrentMethodName() + " : threw an exception.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request threw an exception.");
                }
            } else {
                Logger.error("Answer " + BeanService.getCurrentMethodName() + " : address doesn't match.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Firestation address doesn't match.");
            }
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
    public ResponseEntity<String> deleteFirestationByAddress(@PathVariable("address") String address) {
        Logger.info(
                "Request " + BeanService.getCurrentMethodName() + " : with this address = " + address + ".");
        Firestation oldFirestation = firestationService.findFirestationByAddress(address);
        if (oldFirestation != null) {
            firestationService.deleteFirestationByAddress(address);
            Logger.info(
                    "Answer " + BeanService.getCurrentMethodName() + " :  deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body("Firestation deleted successfully.");
        } else {
            Logger.error("Answer " + BeanService.getCurrentMethodName() + " : firestation address doesn't match.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Firestation address doesn't match.");
        }
    }

    /**
     * Some javadoc.
     * Deletes a firestation based on its station number.
     *
     * @param station The station number of the firestation to be deleted.
     */
    @Transactional
    @DeleteMapping("/station/{station}")
    public ResponseEntity<String> deleteFirestationByStation(@PathVariable("station") String station) {
        Logger.info(
                "Request " + BeanService.getCurrentMethodName() + " : with this station = " + station + ".");
        Firestation oldFirestation = firestationService.findFirestationByStation(station);
        if (oldFirestation != null) {
            firestationService.deleteFirestationByStation(station);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : deleted successfully.");
            return ResponseEntity.status(HttpStatus.OK).body("Firestation deleted successfully.");
        } else {
            Logger.error("Answer " + BeanService.getCurrentMethodName() + " : doesn't match with any Firestation.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Firestation address doesn't match.");
        }
    }
}
