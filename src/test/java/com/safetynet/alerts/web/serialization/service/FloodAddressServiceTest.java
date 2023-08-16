package com.safetynet.alerts.web.serialization.service;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.safetynet.alerts.web.serialization.model.Flood;
import com.safetynet.alerts.web.serialization.model.FloodAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

public class FloodAddressServiceTest {
    @Test
    void testGetFloodList() {
        FloodAddressService floodAddressService = new FloodAddressService();
        List<String> addresses = Arrays.asList("ici", "là-bas");
        List<Flood> floods = new ArrayList<>();

        List<Flood> floods1 = new ArrayList<>();
        Flood flood1 = new Flood("Beraud", "06 66", "ici", 30, null, null);
        Flood flood2 = new Flood("Bruce", "06 68", "ici", 25, null, null);
        floods1.add(flood1);
        floods1.add(flood2);

        List<Flood> floods2 = new ArrayList<>();
        Flood flood3 = new Flood("Will", "06 99", "là-bas", 12, null, null);
        Flood flood4 = new Flood("Wall", "06 99", "là-bas", 10, null, null);
        floods2.add(flood3);
        floods2.add(flood4);

        floods.add(flood1);
        floods.add(flood2);
        floods.add(flood3);
        floods.add(flood4);

        List<FloodAddress> exceptedFloodAddresses = new ArrayList<>();
        FloodAddress floodAddress1 = new FloodAddress("ici", floods1);
        FloodAddress floodAddress2 = new FloodAddress("là-bas", floods2);
        exceptedFloodAddresses.add(floodAddress1);
        exceptedFloodAddresses.add(floodAddress2);

        List<FloodAddress> actualFloodAddresses = floodAddressService.getFloodList(floods, addresses);

        assertEquals(exceptedFloodAddresses, actualFloodAddresses);
    }
}
