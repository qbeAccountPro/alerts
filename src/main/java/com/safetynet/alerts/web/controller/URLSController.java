package com.safetynet.alerts.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.web.logging.EndpointsLogger;
import com.safetynet.alerts.web.service.BeanService;
import com.safetynet.alerts.web.service.URLSService;

/**
 * Some javadoc.
 * 
 * This class represents a REST controller for handling various URLs related to
 * alerts and information.
 */
@RestController
public class URLSController {

    private final URLSService urlsService;
    private EndpointsLogger log = new EndpointsLogger();

    public URLSController(URLSService urlsService) {
        this.urlsService = urlsService;
    }

    /**
     * Some javadoc.
     * 
     * Get fire station data for a specific station number.
     *
     * @param station The station number for which to retrieve fire station data.
     */
    @GetMapping("firestation")
    public void getFireStationData(@RequestParam("stationNumber") String station) {
        // Log the request :
        String methodeName = BeanService.getCurrentMethodName();
        log.request(methodeName);

        urlsService.personCoveredByFireStation(station);
    }

    /**
     * Some javadoc.
     * 
     * Get children living at a specific address along with their family
     * members.
     *
     * @param address The address for which to retrieve children and family members.
     */
    @GetMapping("childAlert")
    public void getChildrenByAddress(@RequestParam("address") String address) {
        // Log the request :
        String methodeName = BeanService.getCurrentMethodName();
        log.request(methodeName);

        urlsService.childrenLivingAtThisAddress(address);
    }

    /**
     * Some javadoc.
     * 
     * Get phone numbers of persons covered by a specific fire station number.
     *
     * @param station The fire station number for which to retrieve phone numbers.
     */
    @GetMapping("phoneAlert")
    public void getResidentsPhoneNumberCoveredByFirestation(@RequestParam("firestation") String station) {
        // Log the request :
        String methodeName = BeanService.getCurrentMethodName();
        log.request(methodeName);

        urlsService.residentsPhoneNumberCoveredByFirestation(station);
    }

    /**
     * Some javadoc.
     * 
     * Retrieves fire station number and persons living at a specific address.
     *
     * @param address The address for which to retrieve fire station number and
     *                persons.
     */
    @GetMapping("fire")
    public void getStationAndPersonsByAddress(@RequestParam("address") String address) { 
        // Log the request :
        String methodeName = BeanService.getCurrentMethodName();
        log.request(methodeName);

        urlsService.residentsAndFireStationAtThisAddress(address);
    }

    /**
     * Some javadoc.
     * 
     * Retrieves persons along with their medical records covered by specific
     * station numbers.
     *
     * @param station The station numbers for which to retrieve persons and medical
     *                records.
     */
    @GetMapping("flood/stations")
    public void getPersonsWithTheirMedicalRecordsByStationNumber(@RequestParam("stations") String station) { 
        // Log the request :
        String methodeName = BeanService.getCurrentMethodName();
        log.request(methodeName);

        urlsService.householdsFromStationNumber(station);
    }

    /**
     * Some javadoc.
     * 
     * Retrieves person information based on their first name and last name.
     *
     * @param firstName The first name of the person for which to retrieve
     *                  information.
     * @param lastName  The last name of the person for which to retrieve
     *                  information.
     */
    @GetMapping("personInfo")
    public void getPersonInfoByFirstNameAndLastName(@RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) { 
        // Log the request :
        String methodeName = BeanService.getCurrentMethodName();
        log.request(methodeName);

        urlsService.personInfoFromFirstAndLastName(firstName, lastName);
    }

    /**
     * Some javadoc.
     * 
     * Retrieves all residents' emails in a specific city.
     *
     * @param city The city for which to retrieve all residents' emails.
     */
    @GetMapping("communityEmail")
    public void getAllResidentsEmails(@RequestParam("city") String city) { 
        // Log the request :
        String methodeName = BeanService.getCurrentMethodName();
        log.request(methodeName);
        
        urlsService.allResidentsEmailsFromCity(city);
    }
}