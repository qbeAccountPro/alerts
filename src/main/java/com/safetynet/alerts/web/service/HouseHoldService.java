package com.safetynet.alerts.web.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.Person;

@Service
public class HouseHoldService {
  List<Household> households;

  public void setHouseholds(List<Household> households) {
    this.households = households;
  }

  /**
   * Some javadoc.
   * 
   * Retrieves a list of all households in the system.
   *
   * @return A list of all households.
   */
  public List<Household> getAllHouseholds() {
    return households;
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
    household.setId(households.size()+1);
    households.add(household);
    return household;
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
    Optional<Household> householdAtAddress = households.stream()
        .filter(household -> household.getAddress().equals(address))
        .findFirst();
    if (householdAtAddress.isPresent()) {
      Household household = householdAtAddress.get();
      return household.getId();
    } else {
      return 0;
    }
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
    Optional<Household> householdAtAddress = households.stream()
        .filter(household -> household.getAddress().equals(address))
        .findFirst();
    if (householdAtAddress.isPresent()) {
      return householdAtAddress.get();
    } else {
      return null;
    }
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
    return households.stream()
        .filter(household -> addresses.contains(household.getAddress()))
        .collect(Collectors.toList());
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
    List<Integer> idHouseholds = firestation.getIdHouseholds();

    return households.stream()
        .filter(household -> idHouseholds.contains(household.getId()))
        .collect(Collectors.toList());
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
    List<Integer> idHouseholds = persons.stream()
        .map(Person::getIdHousehold)
        .collect(Collectors.toList());

    return households.stream()
        .filter(household -> idHouseholds.contains(household.getId()))
        .collect(Collectors.toList());
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
    return households.stream().filter(household -> household.getId() == idHousehold)
        .findFirst().orElse(null);
  }
}