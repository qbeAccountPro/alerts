package com.safetynet.alerts.web.serialization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.safetynet.alerts.web.serialization.model.Flood;
import com.safetynet.alerts.web.serialization.model.FloodAddress;

/**
 * Some javadoc.
 * Service class for generating a list of FloodAddress objects from a list of
 * Flood objects and a list of addresses.
 */
@Service
public class FloodAddressService {

    /**
     * Some javadoc.
     * Generate a list of FloodAddress objects from a list of Flood objects and a
     * list of addresses.
     *
     * @param floods    The list of Flood objects.
     * @param addresses The list of addresses for which floods are reported.
     * @return A list of FloodAddress objects representing floods by address from
     *         the input data.
     */
    public List<FloodAddress> getFloodList(List<Flood> floods, List<String> addresses) {
        List<FloodAddress> floodAddresses = new ArrayList<>();
        for (String address : addresses) {
            FloodAddress floodAddress = new FloodAddress();
            List<Flood> floodsByAddress = new ArrayList<>();
            for (Flood flood : floods) {
                boolean checkAddress = address.equals(flood.getAddress());
                if (checkAddress) {
                    floodsByAddress.add(flood);
                }
            }
            floodAddress.setAddress(address);
            floodAddress.setFlood(floodsByAddress);
            floodAddresses.add(floodAddress);
        }
        return floodAddresses;
    }
}
