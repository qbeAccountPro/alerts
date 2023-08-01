package com.safetynet.alerts.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.web.service.URLSService;

@RestController
public class URLSController {

    private final URLSService urlsService;

    public URLSController(URLSService urlsService) {
        this.urlsService = urlsService;
    }

    @GetMapping("firestation")
    public void getFireStationData(@RequestParam("stationNumber") String station) {
        urlsService.getFireStationData(station);
    }

    @GetMapping("childAlert")
    public void getChildrenByAddress(@RequestParam("address") String address) {
        urlsService.getChildrenByAddress(address);
    }

    @GetMapping("phoneAlert")
    public void getPhoneNumbersByFirestationNumber(@RequestParam("firestation") String station) {
        urlsService.getPhoneNumbersByFirestationNumber(station);
    }

    @GetMapping("fire")
    public void getStationAndPersonsByAddress(@RequestParam("address") String address) {
        urlsService.getStationAndPersonsByAddress(address);
    }

    @GetMapping("flood/stations")
    public void getPersonsWithTheirMedicalRecordsByStationNumber(@RequestParam("stations") String station) {
        urlsService.getPersonsWithTheirMedicalRecordsByStationNumber(station);
    }

    @GetMapping("personInfo")
    public void getPersonInfoByFirstNameAndLastName(@RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        urlsService.getPersonInfoByFirstNameAndLastName(firstName, lastName);
    }

    @GetMapping("communityEmail")
    public void getAllResidentsEmails(@RequestParam("city") String city) {
        urlsService.getAllResidentsEmails(city);
    }
}