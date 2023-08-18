package com.safetynet.alerts.web.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.Serialization;
import com.safetynet.alerts.web.serialization.model.ChildAlert;
import com.safetynet.alerts.web.serialization.model.Fire;
import com.safetynet.alerts.web.serialization.model.Flood;
import com.safetynet.alerts.web.serialization.model.FloodAddress;
import com.safetynet.alerts.web.serialization.model.PersonInfo;
import com.safetynet.alerts.web.serialization.service.ChildAlertService;
import com.safetynet.alerts.web.serialization.service.FireService;
import com.safetynet.alerts.web.serialization.service.FloodAddressService;
import com.safetynet.alerts.web.serialization.service.FloodService;
import com.safetynet.alerts.web.serialization.service.PersonInfoService;

import org.tinylog.Logger;


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

    public URLSService(FirestationService firestationService, PersonService personService,
            MedicalRecordService medicalRecordService, Serialization serialization) {
        this.firestationService = firestationService;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
        this.serialization = serialization;

    }

    /**
     * Some javadoc.
     * Retrieves fire station data based on the provided fire station number.
     *
     * @param station The fire station number for which to retrieve the data.
     */
    public ResponseEntity<String> personCoveredByFireStation(String station) {
        List<String> addresses = firestationService.getAddressesCoveredByStationfireNumber(station);
        List<Person> residents = personService.getPersonsByAddresses(addresses);
        List<MedicalRecord> getAllMedicalRecord = medicalRecordService.getAllMedicalRecord();
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(residents,
                getAllMedicalRecord);
        List<Integer> minorsThenAdultsNumbers = medicalRecordService.getMinorsAndAdultsNumbers(medicalRecords);
        int minorsNumber = minorsThenAdultsNumbers.get(0);
        int adultsNumber = minorsThenAdultsNumbers.get(1);
        if (residents.isEmpty()) {
            serialization.emptyAnswer(BeanService.getCurrentMethodName(), station);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : empty answer.");
            return ResponseEntity.status(HttpStatus.OK).body("Empty answer.");
        } else {
            serialization.firestationSerialization(residents, BeanService.getCurrentMethodName(),
                    station, minorsNumber, adultsNumber);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : successfully generated.");
            return ResponseEntity.status(HttpStatus.OK).body("Successfully generated.");
        }
    }

    /**
     * Some javadoc.
     * Retrieves children and adults living at a specific address based on the
     * provided address.
     *
     * @param address The address for which to retrieve the children and adults.
     */
    public ResponseEntity<String> childrenLivingAtThisAddress(String address) {
        List<Person> persons = personService.getPersonsListByAddress(address);
        List<MedicalRecord> getAllMedicalRecord = medicalRecordService.getAllMedicalRecord();
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons,
                getAllMedicalRecord);
        List<Person> childrenPerson = personService.getChildren(persons, medicalRecords);
        List<Person> adultsPerson = personService.getAdults(persons, medicalRecords);
        ChildAlertService childAlertService = new ChildAlertService();
        List<ChildAlert> children = childAlertService.getChildAlertListFromPersonList(childrenPerson,
                medicalRecords);
        List<ChildAlert> adults = childAlertService.getChildAlertListFromPersonList(adultsPerson, medicalRecords);
        if (children.isEmpty() && adultsPerson.isEmpty()) {
            serialization.emptyAnswer(BeanService.getCurrentMethodName(), address);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : empty answer.");
            return ResponseEntity.status(HttpStatus.OK).body("Empty answer.");
        } else {
            serialization.childAlertSerialization(children, adults,
                    BeanService.getCurrentMethodName(), address);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : successfully generated.");
            return ResponseEntity.status(HttpStatus.OK).body("Successfully generated.");
        }
    }

    /**
     * Some javadoc.
     * Retrieves phone numbers of persons living in the area covered by a specific
     * fire station based on the provided fire station number.
     *
     * @param station The fire station number for which to retrieve the phone
     *                numbers.
     */
    public ResponseEntity<String> phoneNumberOfResidentsCoveredByFireStation(String station) {
        List<String> addresses = firestationService.getAddressesCoveredByStationfireNumber(station);
        List<Person> persons = personService.getPersonsByAddresses(addresses);
        if (persons.isEmpty()) {
            serialization.emptyAnswer(BeanService.getCurrentMethodName(), station);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : empty answer.");
            return ResponseEntity.status(HttpStatus.OK).body("Empty answer.");
        } else {
            serialization.phoneAlertSerialization(persons, BeanService.getCurrentMethodName(), station);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : successfully generated.");
            return ResponseEntity.status(HttpStatus.OK).body("Successfully generated.");

        }
    }

    /**
     * Some javadoc.
     * Retrieves fire information and persons living at a specific address based on
     * the provided address.
     *
     * @param address The address for which to retrieve the fire information and
     *                persons.
     */
    public ResponseEntity<String> residentsAndFireStationAtThisAddress(String address) {
        FireService fireService = new FireService();
        List<Person> persons = personService.getPersonsListByAddress(address);
        List<MedicalRecord> getAllMedicalRecord = medicalRecordService.getAllMedicalRecord();

        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons,
                getAllMedicalRecord);
        String firestationNumber = firestationService.getStationNumberByAdress(address);
        List<Fire> fires = fireService.getFireList(persons, medicalRecords);

        if (fires.isEmpty()) {
            serialization.emptyAnswer(BeanService.getCurrentMethodName(), address);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : empty answer.");
            return ResponseEntity.status(HttpStatus.OK).body("Empty answer.");
        } else {
            serialization.fireSerialization(fires, firestationNumber, BeanService.getCurrentMethodName(),
                    address);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : successfully generated.");
            return ResponseEntity.status(HttpStatus.OK).body("Successfully generated.");

        }
    }

    /**
     * Some javadoc.
     * Retrieves persons with their medical records based on the provided fire
     * station number.
     *
     * @param station The fire station number for which to retrieve persons and
     *                their medical records.
     */
    public ResponseEntity<String> personsByAddressFromStationNumber(String station) {
        FloodService floodService = new FloodService();
        FloodAddressService floodAddressService = new FloodAddressService();
        List<String> addresses = firestationService.getAddressesCoveredByStationfireNumber(station);
        List<Person> persons = personService.getPersonsByAddresses(addresses);
        List<MedicalRecord> getAllMedicalRecord = medicalRecordService.getAllMedicalRecord();

        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons,
                getAllMedicalRecord);
        List<Flood> floods = floodService.getFloodList(persons, medicalRecords);
        List<FloodAddress> floodAddresses = floodAddressService.getFloodList(floods, addresses);
        if (floodAddresses.isEmpty()) {
            serialization.emptyAnswer(BeanService.getCurrentMethodName(), station);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : empty answer.");
            return ResponseEntity.status(HttpStatus.OK).body("Empty answer.");
        } else {
            serialization.floodSerialization(floodAddresses, BeanService.getCurrentMethodName(), station);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : successfully generated.");
            return ResponseEntity.status(HttpStatus.OK).body("Successfully generated.");

        }
    }

    /**
     * Some javadoc.
     * Retrieves email addresses of all residents living in a specific city based on
     * the provided city name.
     *
     * @param city The city for which to retrieve the email addresses of residents.
     */
    public ResponseEntity<String> personInfoFromFirstAndLastName(String firstName, String lastName) {
        PersonInfoService personInfoService = new PersonInfoService();
        List<Person> persons = personService.getPersonsByFirstNameAndLastName(firstName, lastName);
        List<MedicalRecord> getAllMedicalRecord = medicalRecordService.getAllMedicalRecord();

        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons,
                getAllMedicalRecord);
        List<PersonInfo> personsInfo = personInfoService.getPersonInfoList(persons, medicalRecords);

        if (personsInfo.isEmpty()) {
            serialization.emptyAnswer(BeanService.getCurrentMethodName(), firstName + " " + lastName);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : empty answer.");
            return ResponseEntity.status(HttpStatus.OK).body("Empty answer.");
        } else {
            serialization.personInfoSerialization(personsInfo, BeanService.getCurrentMethodName(), firstName, lastName);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : successfully generated.");
            return ResponseEntity.status(HttpStatus.OK).body("Successfully generated.");
        }
    }

    /**
     * Some javadoc.
     * Retrieves email addresses of all residents living in a specific city based on
     * the provided city name.
     *
     * @param city The city for which to retrieve the email addresses of residents.
     */
    public ResponseEntity<String> allResidentsEmailsFromCity(String city) {
        List<Person> persons = personService.getPersonsByCity(city);
        if (persons.isEmpty()) {
            serialization.emptyAnswer(BeanService.getCurrentMethodName(), city);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : empty answer.");
            return ResponseEntity.status(HttpStatus.OK).body("Empty answer.");
        } else {
            serialization.communityEmailSerialization(persons, BeanService.getCurrentMethodName(), city);
            Logger.info("Answer " + BeanService.getCurrentMethodName() + " : successfully generated.");
            return ResponseEntity.status(HttpStatus.OK).body("Successfully generated.");
        }
    }

}
