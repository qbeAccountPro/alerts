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

  /**
   * Some javadoc.
   * 
   * Retrieves a list of all households in the system.
   *
   * @return A list of all households.
   */
  public List<Household> getAllHouseholds() {
    return householdDao.findAll();
  }

  /**
   * Some javadoc.
   * 
   * Saves a new household with the provided address in the system.
   *
   * @param address The address of the household to be saved.
   * @return The saved household.
   */
  public Household saveHousehold(String address) {
    Household household = new Household();
    household.setAddress(address);
    return householdDao.save(household);
  }

  /**
   * Some javadoc.
   * 
   * Retrieves the ID of a household by its address.
   *
   * @param address The address of the household to retrieve the ID for.
   * @return The ID of the household, or 0 if not found.
   */
  public int getId(String address) {
    Household household = householdDao.findByAddress(address);
    return household.getId();
  }

  /**
   * Some javadoc.
   * 
   * Retrieves a household by its address.
   *
   * @param address The address of the household to retrieve.
   * @return The household associated with the provided address, or null if not
   *         found.
   */
  public Household getHouseholdByAddress(String address) {
    return householdDao.findByAddress(address);
  }

  /**
   * Some javadoc.
   * 
   * Retrieves a list of households by a list of addresses.
   *
   * @param addresses The list of addresses for which to retrieve households.
   * @return A list of households associated with the provided addresses.
   */
  public List<Household> getHouseholdsByAddresses(List<String> addresses) {
    List<Household> households = new ArrayList<>();
    for (String address : addresses) {
      Household household = householdDao.findByAddress(address);
      households.add(household);
    }
    return households;
  }

  /**
   * Some javadoc.
   * 
   * Retrieves a list of households associated with a firestation.
   *
   * @param firestation The firestation for which to retrieve associated
   *                    households.
   * @return A list of households associated with the provided firestation.
   */
  public List<Household> getHouseholdsByFirestation(Firestation firestation) {
    List<Household> households = new ArrayList<>();
    for (Integer idHousehold : firestation.getIdHouseholds()) {
      Optional<Household> householdOptional = householdDao.findById(idHousehold);
      householdOptional.ifPresent(households::add);
    }
    return households;
  }

  /**
   * Some javadoc.
   * 
   * Retrieves a list of households associated with a list of persons.
   *
   * @param persons The list of persons for which to retrieve associated
   *                households.
   * @return A list of households associated with the provided persons.
   */
  public List<Household> getHouseholdsByPersons(List<Person> persons) {
    List<Household> households = new ArrayList<>();
    for (Person person : persons) {
      Optional<Household> householdOptional = householdDao.findById(person.getIdHousehold());
      householdOptional.ifPresent(households::add);
    }
    return households;
  }

  /**
   * Some javadoc.
   * 
   * Retrieves a household by its ID.
   *
   * @param idHousehold The ID of the household to retrieve.
   * @return The household associated with the provided ID, or null if not found.
   */
  public Household getHouseholdById(int idHousehold) {
    Optional<Household> householdOptional = householdDao.findById(idHousehold);
    return householdOptional.orElse(null);
  }
}