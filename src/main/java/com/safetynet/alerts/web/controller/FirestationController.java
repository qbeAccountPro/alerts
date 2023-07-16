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

import com.safetynet.alerts.web.dao.FirestationDao;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.service.BeanService;

@RestController
@RequestMapping("/firestation")
public class FirestationController {

    private final FirestationDao firestationDao;

    public FirestationController(FirestationDao firestationDao) {
        this.firestationDao = firestationDao;
    }

    @GetMapping("")
    public List<Firestation> list() {
        return firestationDao.findAll();
    }

    @PostMapping("")
    public void addFirestation(@RequestBody Firestation firestation) {
        firestationDao.save(firestation);
    }

    @PutMapping("/{address}")
    public void updateNumberStationFromAddress(@PathVariable("address") String address,
            @RequestBody Firestation newFirestation) {
        Firestation oldFirestation = firestationDao.findByAddress(address);
        if (oldFirestation != null) {
            try {
                Firestation updateFirestation = BeanService.updateBeanWithNotNullPropertiesFromNewObject(oldFirestation,
                        newFirestation);
                updateFirestation.setId(oldFirestation.getId());
                firestationDao.save(updateFirestation);
            } catch (Exception e) {
                System.out.println("updateByFirstNameAndLastName produit l'erreur : " + e);
            }
        }
    }

    @Transactional
    @DeleteMapping("/{address}")
    public void deleteFirestationByAddress(@PathVariable("address") String address) {
        firestationDao.deleteByAddress(address);
    }

}
