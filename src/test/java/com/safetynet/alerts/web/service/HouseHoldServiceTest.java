package com.safetynet.alerts.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.Person;

@ExtendWith(MockitoExtension.class)
public class HouseHoldServiceTest {

  @InjectMocks
  private HouseHoldService houseHoldService;

  private List<String> addresses = new ArrayList<>();
  private String ADDRESS_1 = "Rue du chemin";
  private String ADDRESS_2 = "Rue du puit";

  private List<Household> households = new ArrayList<>();
  private Household household_1 = new Household(1, ADDRESS_1);
  private Household household_2 = new Household(2, ADDRESS_1);

  @BeforeEach
  public void setUp() {
    addresses.add(ADDRESS_1);
    addresses.add(ADDRESS_2);

    households.add(household_1);
    households.add(household_2);

    houseHoldService.setHouseholds(households);
  }

  @Test
  void testGetAllHouseholds() {
    houseHoldService.getAllHouseholds();
  }

  @Test
  void testGetHouseholdByAddress() {
    houseHoldService.getHouseholdByAddress(ADDRESS_1);
  }

  @Test
  void testGetHouseholdsByAddresses() {

    List<Household> result = houseHoldService.getHouseholdsByAddresses(addresses);

    assertEquals(households, result);
  }

  @Test
  void testGetId() {
    houseHoldService.getId(ADDRESS_1);
  }

  @Test
  void testSaveHousehold() {
    houseHoldService.saveHousehold(ADDRESS_1);
    household_1.setId(0);
  }

  @Test
  void testGetHouseholdsByFirestation() {
    List<Integer> idHouseholds = new ArrayList<>();
    idHouseholds.add(1);
    idHouseholds.add(2);
    Firestation firestation = new Firestation(0, idHouseholds, ADDRESS_1);
    List<Household> result = houseHoldService.getHouseholdsByFirestation(firestation);

    assertEquals(households, result);
  }

  @Test
  void testGetHouseholdsByPersons() {
    List<Person> persons = new ArrayList<>();
    Person person_1 = new Person();
    person_1.setIdHousehold(1);
    Person person_2 = new Person();
    person_2.setIdHousehold(2);
    persons.add(person_1);
    persons.add(person_2);

    List<Household> result = houseHoldService.getHouseholdsByPersons(persons);

    assertEquals(households, result);
  }

  @Test
  void testGetHouseholdById() {

    Household result = houseHoldService.getHouseholdById(household_1.getId());

    assertEquals(household_1, result);
  }
}