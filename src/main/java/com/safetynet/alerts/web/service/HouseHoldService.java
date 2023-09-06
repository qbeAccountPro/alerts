package com.safetynet.alerts.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.dao.HouseholdDao;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.Person;

@Service
public class HouseHoldService {

    private final HouseholdDao householdDao;

    @Autowired
    public HouseHoldService(HouseholdDao householdDao) {
        this.householdDao = householdDao;
    }

    public List<Household> getAllHouseholds() {
        return householdDao.findAll();
    }

    public Household saveHousehold(String address) {
        Household household = new Household();
        household.setAddress(address);
        return householdDao.save(household);
    }

    public int getId(String address) {
        Household household = householdDao.findByAddress(address);
        return household.getId();
    }

    public Household getHouseholdByAddress(String address) {
        return householdDao.findByAddress(address);
    }

    public List<Household> getHouseholdsByAddresses(List<String> addresses) {
        List<Household> households = new ArrayList<>();
        for (String address : addresses) {
            Household household = householdDao.findByAddress(address);
            households.add(household);
        }
        return households;
    }

    public List<Household> getHouseholdsByFirestation(Firestation firestation) {
        List<Household> households = new ArrayList<>();
        for (Integer idHousehold : firestation.getIdHouseholds()) {
            Optional<Household> householdOptional = householdDao.findById(idHousehold);
            householdOptional.ifPresent(households::add);
        }
        return households;
    }

    public List<Household> getHouseholdsByPersons(List<Person> persons) {
        List<Household> households = new ArrayList<>();
        for (Person person : persons) {
            Optional<Household> householdOptional = householdDao.findById(person.getId());
            householdOptional.ifPresent(households::add);
        }
        return households;
    }

    public Household getHouseholdById(int idHousehold) {
        Optional<Household> householdOptional = householdDao.findById(idHousehold);
        return householdOptional.orElse(null);
    }
}