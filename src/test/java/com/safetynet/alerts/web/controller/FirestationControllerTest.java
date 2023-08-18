package com.safetynet.alerts.web.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.service.FirestationService;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class FirestationControllerTest {

    @Mock
    private FirestationService firestationService;

    @InjectMocks
    private FirestationController firestationController;

    private Firestation firestation_1, firestation_2;
    private String address, station;

    @BeforeEach
    public void setUp() {
        station = "1";
        address = "123 Main St";
        firestation_1 = new Firestation(1, address, "Station_1");
        firestation_2 = new Firestation(1, address, "Station_2");
    }

    /**
     * SomeJavadoc.
     * Test case for adding a fire station.
     */
    @Test
    public void testAddFirestation() {
        firestationController.addFirestation(firestation_1);
        verify(firestationService, times(1)).saveFirestation(firestation_1);
    }

    /**
     * SomeJavadoc.
     * Test case for updating a fire station's station number by address when old fire station exists.
     */
    @Test
    public void testUpdateStationFromAddressIfOldFirestationIsNotNull() {
        when(firestationService.findFirestationByAddress(address)).thenReturn(firestation_1);
        firestationController.updateNumberStationFromAddress(address, firestation_2);
        verify(firestationService, times(1)).findFirestationByAddress(address);
        verify(firestationService, times(1)).saveFirestation(firestation_2);
    }

    /**
     * SomeJavadoc.
     * Test case for updating a fire station's station number by address when old
     * fire station is null.
     */
    @Test
    public void testUpdateStationFromAddressIfOldFirestationIsNull() {
        firestation_1 = null;
        when(firestationService.findFirestationByAddress(address)).thenReturn(firestation_1);
        firestationController.updateNumberStationFromAddress(address, firestation_2);
        verify(firestationService, times(1)).findFirestationByAddress(address);
        verify(firestationService, times(0)).saveFirestation(any(Firestation.class));
    }

    /**
     * SomeJavadoc.
     * Test case for deleting a fire station by address.
     */
    @Test
    public void testDeleteFirestationByAddress() {
        when(firestationService.findFirestationByAddress(address)).thenReturn(firestation_1);
        firestationController.deleteFirestationByAddress(address);
        verify(firestationService, times(1)).deleteFirestationByAddress(address);
    }

    /**
     * SomeJavadoc.
     * Test case for deleting a fire station by station number.
     */
    @Test
    public void testDeleteFirestationByStation() {
        when(firestationService.findFirestationByStation(station)).thenReturn(firestation_1);
        firestationController.deleteFirestationByStation(station);
        verify(firestationService, times(1)).deleteFirestationByStation(station);
    }
}
