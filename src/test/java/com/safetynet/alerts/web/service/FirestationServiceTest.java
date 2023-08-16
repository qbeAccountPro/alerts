package com.safetynet.alerts.web.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.web.dao.FirestationDao;
import com.safetynet.alerts.web.model.Firestation;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class FirestationServiceTest {

  @Mock
  private FirestationDao firestationDao;

  @InjectMocks
  private FirestationService firestationService;

  private Firestation firestation_1, firestation_2;
  private List<Firestation> expectedFirestations;
  private String firestationNumber, address_1;
  private List<String> expectedAddresses;

  @BeforeEach
  public void setUp() {
    address_1 = "Rue de 123";
    firestationNumber = "Station_1";
    expectedAddresses = new ArrayList<>();
    expectedAddresses.add(address_1);
    firestation_1 = new Firestation(1, address_1, firestationNumber);
    firestation_2 = new Firestation(1, "456 Rue du", "Station_2");
    expectedFirestations = new ArrayList<>();
    expectedFirestations.add(firestation_1);
    expectedFirestations.add(firestation_2);
  }

  /**
   * SomeJavadoc.
   * Test for 'getAllFirestations' method.
   */
    @Test
    public void testGetAllFirestations() {
        when(firestationDao.findAll()).thenReturn(expectedFirestations);
        List<Firestation> result = firestationService.getAllFirestations();
        assertEquals(expectedFirestations, result);
    }

  /**
   * SomeJavadoc.
   * Test for 'getAddressesCoveredByStationfireNumber' method.
   */
    @Test
    public void testGetAddressesCoveredByStationfireNumber() {
        when(firestationDao.findAll()).thenReturn(expectedFirestations);
        List<String> result = firestationService.getAddressesCoveredByStationfireNumber(firestationNumber);
        assertEquals(expectedAddresses, result);
    }

  /**
   * SomeJavadoc.
   * Test for 'saveFirestation' method.
   */
  @Test
  public void testSaveFirestation() {
    firestationService.saveFirestation(firestation_1);
    verify(firestationDao, times(1)).save(firestation_1);
  }

  /**
   * SomeJavadoc.
   * Test for 'findFirestationByAddress' method.
   */
    @Test
    public void testFindFirestationByAddress() {
        when(firestationDao.findByAddress(address_1)).thenReturn(firestation_1);
        Firestation result = firestationService.findFirestationByAddress(address_1);
        assertEquals(firestation_1, result);
    }

  /**
   * SomeJavadoc.
   * Test for 'deleteFirestationByAddress' method.
   */
  @Test
  public void testDeleteFirestationByAddress() {
    firestationService.deleteFirestationByAddress(address_1);
    verify(firestationDao, times(1)).deleteByAddress(address_1);
  }

  /**
   * SomeJavadoc.
   * Test for 'deleteFirestationByStation' method.
   */
  @Test
  public void testDeleteFirestationByStation() {
    firestationService.deleteFirestationByStation(firestationNumber);
    verify(firestationDao, times(1)).deleteByStation(firestationNumber);
  }

    /**
     * SomeJavadoc.
     * Test for 'getStationNumberByAdress' method.
     */
    @Test
    public void testGetStationNumberByAddress() {
        when(firestationDao.findAll()).thenReturn(expectedFirestations);
        String result = firestationService.getStationNumberByAdress(address_1);
        assertEquals(firestationNumber, result);
    }
}
