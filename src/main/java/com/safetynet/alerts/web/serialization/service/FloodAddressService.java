package com.safetynet.alerts.web.serialization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import com.safetynet.alerts.web.serialization.model.Flood;
import com.safetynet.alerts.web.serialization.model.FloodAddress;

@Service
public class FloodAddressService {

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
