package com.safetynet.alerts.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.safetynet.alerts.web.communUtilts.DataManipulationUtils;
import com.safetynet.alerts.web.logging.EndpointsLogger;
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
  public void getPersonCoveredByFirestation(@RequestParam("stationNumber") String station) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);

    urlsService.personCoveredByFireStation(station);
  }

  /**
   * Some javadoc.
   * 
   * Get children living at a specific address with their family.
   *
   * @param address for which to retrieve children and family members.
   */
  @GetMapping("childAlert")
  public void getChildrenLivingAtThisAddress(@RequestParam("address") String address) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);

    urlsService.childrenLivingAtThisAddress(address);
  }

  /**
   * Some javadoc.
   * 
   * Get phone numbers of persons covered by a specific fire station number.
   *
   * @param station number for which to retrieve phone numbers.
   */
  @GetMapping("phoneAlert")
  public void getPersonsPhoneNumbersCoveredByStation(@RequestParam("firestation") String station) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);

    urlsService.personsPhoneNumbersCoveredByStation(station);
  }

  /**
   * Some javadoc.
   * 
   * Retrieves fire station and persons living at a specific address.
   *
   * @param address for which to retrieve station number and
   *                persons.
   */
  @GetMapping("fire")
  public void getStationAndPersonsByAddress(@RequestParam("address") String address) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);

    urlsService.stationAndPersonsByAddress(address);
  }

  /**
   * Some javadoc.
   * 
   * Retrieves persons with their medical records covered by specific station.
   *
   * @param station number for which to retrieve persons.
   */
  @GetMapping("flood/stations")
  public void getPersonsByHouseholdsFromStration(@RequestParam("stations") String station) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);

    urlsService.personsByHouseholdsFromStation(station);
  }

  /**
   * Some javadoc.
   * 
   * Retrieves person information based on their first name and last name.
   *
   * @param firstName of the person.
   * @param lastName  of the person.
   */
  @GetMapping("personInfo")
  public void getPersonInfoByFirstAndLastName(@RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);

    urlsService.personInfoByFirstAndLastName(firstName, lastName);
  }

  /**
   * Some javadoc.
   * 
   * Retrieves all residents' emails in a specific city.
   *
   * @param city for which to retrieve all residents' emails.
   */
  @GetMapping("communityEmail")
  public void getAllResidentsEmails(@RequestParam("city") String city) {
    // Log the request :
    String methodeName = DataManipulationUtils.getCurrentMethodName();
    log.request(methodeName);

    urlsService.allResidentsEmailsFromCity(city);
  }
}