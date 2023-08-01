package com.safetynet.alerts.web.controller;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.service.BeanService;
import com.safetynet.alerts.web.service.FirestationService;

@RestController
@RequestMapping("/firestation")
public class FirestationController {

    private final FirestationService firestationService;

    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }
/* 
    @GetMapping("")
    public List<Firestation> getAllFirestations() {
        return firestationService.getAllFirestations();
    } */

    @PostMapping("")
    public void addFirestation(@RequestBody Firestation firestation) {
        firestationService.saveFirestation(firestation);
    }

    @PutMapping("/{address}")
    public void updateNumberStationFromAddress(@PathVariable("address") String address,
            @RequestBody Firestation newFirestation) {
        Firestation oldFirestation = firestationService.findFirestationByAddress(address);
        if (oldFirestation != null) {
            try {
                Firestation updateFirestation = BeanService.updateBeanWithNotNullPropertiesFromNewObject(oldFirestation,
                        newFirestation);
                updateFirestation.setId(oldFirestation.getId());
                firestationService.saveFirestation(updateFirestation);
            } catch (Exception e) {
                System.out.println("updateByFirstNameAndLastName produit l'erreur : " + e);
            }
        }
    }

    @Transactional
    @DeleteMapping("/{address}")
    public void deleteFirestationByAddress(@PathVariable("address") String address) {
        firestationService.deleteFirestationByAddress(address);
    }

    @Transactional
    @DeleteMapping("/{station}")
    public void deleteFirestationByStation(@PathVariable("station") String station) {
        firestationService.deleteFirestationByStation(station);
    }

    @GetMapping("?stationNumber={station_number}")
    public void getPersonsCoveredByFirestationNumber(@PathVariable("stationNumber") String stationNumber) {
        firestationService.getPersonsCoveredByFirestationNumber(stationNumber);

    }

}
