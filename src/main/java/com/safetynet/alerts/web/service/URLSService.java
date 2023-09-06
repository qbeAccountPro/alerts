package com.safetynet.alerts.web.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.logging.EndpointsLogger;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.Serialization;
import com.safetynet.alerts.web.serialization.model.ChildAlert;
import com.safetynet.alerts.web.serialization.model.FireAlert;
import com.safetynet.alerts.web.serialization.model.FloodAlertByHousehold;
import com.safetynet.alerts.web.serialization.model.FirestationAlert;
import com.safetynet.alerts.web.serialization.model.PersonInfoAlert;
import com.safetynet.alerts.web.serialization.service.ChildAlertService;
import com.safetynet.alerts.web.serialization.service.FireService;
import com.safetynet.alerts.web.serialization.service.FloodService;
import com.safetynet.alerts.web.serialization.service.PersonCoveredService;
import com.safetynet.alerts.web.serialization.service.PersonInfoService;

/**
 * Some javadoc.
 * This service class provides operations related to handling URLs and their
 * corresponding data retrieval.
 * It interacts with other service classes like FirestationService,
 * PersonService, and MedicalRecordService to retrieve data and perform
 * operations based on URL parameters.
 */
@Service
public class URLSService {

    private final FirestationService firestationService;
    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;
    private final Serialization serialization;
    private final HouseHoldService houseHoldService;
    private EndpointsLogger log = new EndpointsLogger();

    public URLSService(FirestationService firestationService, PersonService personService,
            MedicalRecordService medicalRecordService, Serialization serialization, HouseHoldService houseHoldService) {
        this.firestationService = firestationService;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
        this.serialization = serialization;
        this.houseHoldService = houseHoldService;
    }

    /**
     * Some javadoc.
     * 
     * Retrieves fire station data based on the provided fire station number.
     *
     * @param station The fire station number for which to retrieve the data.
     */
    public ResponseEntity<String> personCoveredByFireStation(String station) {
        String methodeName = BeanService.getCurrentMethodName();
        Firestation firestation = firestationService.getFirestationByStation(station);
        List<Household> households = houseHoldService.getHouseholdsByFirestation(firestation);
        List<Person> persons = personService.getPersonsByHouseholds(households);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons);
        PersonCoveredService personCoveredService = new PersonCoveredService();
        List<FirestationAlert> personsCovered = personCoveredService.getPersonCoveredList(persons, households);

        int adults = medicalRecordService.getAdultsNumber(medicalRecords);
        int minors = medicalRecordService.getMinorsNumber(medicalRecords);
        if (persons.isEmpty()) {
            return serialization.emptyAnswer(methodeName, station);
        } else {
            return serialization.firestationSerialization(personsCovered, methodeName, station, minors, adults);
        }
    }

    /**
     * Some javadoc.
     * 
     * Retrieves children and adults living at a specific address based on the
     * provided address.
     *
     * @param address The address for which to retrieve the children and adults.
     */
    public ResponseEntity<String> childrenLivingAtThisAddress(String address) {
        String methodeName = BeanService.getCurrentMethodName();
        // Household at this address :
        Household household = houseHoldService.getHouseholdByAddress(address);
        // Persons at this address :
        List<Person> persons = personService.getPersonsByHousehold(household);
        // MedicalRecords at this address :
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons);
        // Separate adults and minors MedicalRecords :
        List<MedicalRecord> adultsMedicalRecords = medicalRecordService.getAdultsMedicalRecords(medicalRecords);
        List<MedicalRecord> childrenMedicalRecords = medicalRecordService.getChildrenMedicalRecords(medicalRecords);

        ChildAlertService childAlertService = new ChildAlertService();
        List<ChildAlert> children = childAlertService.getChildAlertListFromPersonList(persons, childrenMedicalRecords);
        List<ChildAlert> adults = childAlertService.getChildAlertListFromPersonList(persons, adultsMedicalRecords);
        if (children.isEmpty() && adults.isEmpty()) {
            serialization.emptyAnswer(methodeName, address);
            return log.emptyAnswer(methodeName);
        } else {
            serialization.childAlertSerialization(children, adults, methodeName, address);
            return log.successfullyGenerated(methodeName);
        }
    }

    /*
     * Some javadoc.
     * 
     * Retrieves phone numbers of persons living in the area covered by a specific
     * fire station based on the provided fire station number.
     * 
     * @param station The fire station number for which to retrieve the phone
     * numbers.
     */
    public ResponseEntity<String> residentsPhoneNumberCoveredByFirestation(String station) {
        String methodeName = BeanService.getCurrentMethodName();
        Firestation firestation = firestationService.getFirestationByStation(station);
        List<Household> households = houseHoldService.getHouseholdsByFirestation(firestation);
        List<Person> persons = personService.getPersonsByHouseholds(households);
        if (persons.isEmpty()) {
            return serialization.emptyAnswer(methodeName, station);
        } else {
            return serialization.phoneAlertSerialization(persons,
                    methodeName, station);
        }
    }

    /*
     * Some javadoc.
     * 
     * Retrieves fire information and persons living at a specific address based on
     * the provided address.
     * 
     * @param address The address for which to retrieve the fire information and
     * persons.
     * 
     */
    public ResponseEntity<String> residentsAndFireStationAtThisAddress(String address) {
        String methodeName = BeanService.getCurrentMethodName();
        Household household = houseHoldService.getHouseholdByAddress(address);
        List<Firestation> firestations = firestationService.getFirestationsByHousehold(household);
        List<Person> persons = personService.getPersonsByHousehold(household);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons);
        FireService fireService = new FireService();
        List<FireAlert> fires = fireService.getFireList(persons, medicalRecords);
        if (fires.isEmpty()) {
            return serialization.emptyAnswer(methodeName, address);
        } else {
            String stations = new String();
            for (Firestation firestation : firestations) {
                if (stations.isBlank()) {
                    stations = firestation.getStation();
                } else {
                    stations = stations + "," + firestation.getStation();
                }
            }
            return serialization.fireSerialization(fires, stations, methodeName, address);
        }
    }

    /*
     * Some javadoc.
     * 
     * Retrieves persons with their medical records based on the provided fire
     * station number.
     * 
     * @param station The fire station number for which to retrieve persons and
     * their medical records.
     */
    public ResponseEntity<String> householdsFromStationNumber(String station) {
        String methodeName = BeanService.getCurrentMethodName();
        FloodService floodService = new FloodService();
        Firestation firestation = firestationService.getFirestationByStation(station);
        List<Household> households = houseHoldService.getHouseholdsByFirestation(firestation);
        List<Person> persons = personService.getPersonsByHouseholds(households);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons);
        List<FloodAlertByHousehold> floodAlertByHousehold = floodService.getFloodAlertByHousehold(persons,
                medicalRecords, households);
        if (floodAlertByHousehold.isEmpty()) {
            return serialization.emptyAnswer(methodeName, station);
        } else {
            return serialization.floodSerialization(floodAlertByHousehold, methodeName, station);
        }
    }

    /*
     * Some javadoc.
     * 
     * Retrieves email addresses of all residents living in a specific city based on
     * the provided city name.
     * 
     * @param city The city for which to retrieve the email addresses of residents.
     */
    public ResponseEntity<String> personInfoFromFirstAndLastName(String firstName, String lastName) {
        String methodeName = BeanService.getCurrentMethodName();

        PersonInfoService personInfoService = new PersonInfoService();
        Person person = personService.getPersonByFirstAndLastName(firstName, lastName);
        MedicalRecord medicalRecords = medicalRecordService.getMedicalRecordByPerson(person);
        Household household = houseHoldService.getHouseholdById(person.getIdHousehold());
        List<PersonInfoAlert> personInfo = personInfoService.getPersonInfo(person, medicalRecords, household);
        if (personInfo == null) {
            return serialization.emptyAnswer(methodeName, firstName + " "
                    + lastName); 
        } else {
            return serialization.personInfoSerialization(personInfo,
                    methodeName, firstName, lastName);
        }
    }

    /*
     * Some javadoc.
     * Retrieves email addresses of all residents living in a specific city based on
     * the provided city name.
     * 
     * @param city The city for which to retrieve the email addresses of residents.
     * 
     */
    public ResponseEntity<String> allResidentsEmailsFromCity(String city) {
        String methodeName = BeanService.getCurrentMethodName();

        List<Person> persons = personService.getPersonsByCity(city);
        if (persons.isEmpty()) {
            return serialization.emptyAnswer(methodeName, city);

        } else {
            return serialization.communityEmailSerialization(persons,
                    methodeName, city);
        }
    }
}