package com.safetynet.alerts.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.dao.FirestationDao;
import com.safetynet.alerts.web.model.Firestation;

@Service
public class FirestationService {

    private final FirestationDao firestationDao;

    @Autowired
    public FirestationService(FirestationDao firestationDao) {
        this.firestationDao = firestationDao;
    }

    public List<Firestation> getAllFirestations() {
        return firestationDao.findAll();
    }

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

    public void saveFirestation(Firestation firestation) {
        firestationDao.save(firestation);
    }

    public Firestation findFirestationByAddress(String address) {
       return firestationDao.findByAddress(address);
    }

    public void deleteFirestationByAddress(String address) {
        firestationDao.deleteByAddress(address);
    }

    public void deleteFirestationByStation(String station) {
        firestationDao.deleteByStation(station);
    }

    public void getPersonsCoveredByFirestationNumber(String station) {
        System.out.println("weshwesh");
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
