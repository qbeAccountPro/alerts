package com.safetynet.alerts.web.model;

public class firestation {
    private String address;
    private String station;

    public firestation() {

    }

    public firestation(String address, String station) {
        this.address = address;
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "firestation [address=" + address + ", station=" + station + "]";
    }
}
