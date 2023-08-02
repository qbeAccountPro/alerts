package com.safetynet.alerts.web.service;

import java.util.List;
import java.util.Arrays;

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

    public void getFireStationData(String station) {
        List<String> addresses = firestationService.getAddressesCoveredByStationfireNumber(station);
        List<Person> residents = personService.getPersonsByAddresses(addresses);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(residents);
        List<Integer> minorsThenAdultsNumbers = medicalRecordService.getMinorsAndAdultsNumbers(medicalRecords);
        int minorsNumber = minorsThenAdultsNumbers.get(0);
        int adultsNumber = minorsThenAdultsNumbers.get(1);
        serialization.firestationSerialization(residents, getCurrentMethodName(),
                station, minorsNumber, adultsNumber);
    }

    public void getChildrenByAddress(String address) {
        List<Person> persons = personService.getPersonsListByAddress(address);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons);
        List<Person> childrenPerson = personService.getChildren(persons, medicalRecords);
        List<Person> adultsPerson = personService.getAdults(persons, medicalRecords);
        ChildAlertService childAlertService = new ChildAlertService();
        List<ChildAlert> children = childAlertService.getChildAlertListFromPersonList(childrenPerson,
                medicalRecords);
        serialization.childAlertSerialization(children, adultsPerson,
                getCurrentMethodName(), address);
    }

    public void getPhoneNumbersByFirestationNumber(String station) {
        List<String> addresses = firestationService.getAddressesCoveredByStationfireNumber(station);
        List<Person> persons = personService.getPersonsByAddresses(addresses);
        serialization.phoneAlertSerialization(persons, getCurrentMethodName(), station);
    }

    public void getStationAndPersonsByAddress(String address) {
        FireService fireService = new FireService();
        List<Person> persons = personService.getPersonsListByAddress(address);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons);
        String firestationNumber = firestationService.getStationNumberByAdress(address);
        List<Fire> fires = fireService.getFireList(persons, medicalRecords);
        serialization.fireSerialization(fires, firestationNumber, getCurrentMethodName(),
                address);
    }

    public void getPersonsWithTheirMedicalRecordsByStationNumber(String station) {
        FloodService floodService = new FloodService();
        FloodAddressService floodAddressService = new FloodAddressService();
        List<String> addresses = firestationService.getAddressesCoveredByStationfireNumber(station);
        List<Person> persons = personService.getPersonsByAddresses(addresses);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons);
        List<Flood> floods = floodService.getFloodList(persons, medicalRecords);
        List<FloodAddress> floodAddresses = floodAddressService.getFloodList(floods, addresses);
        serialization.floodSerialization(floodAddresses, getCurrentMethodName(), station);
    }

    public void getPersonInfoByFirstNameAndLastName(String firstName, String lastName) {
        PersonInfoService personInfoService = new PersonInfoService();
        List<Person> persons = personService.getPersonsByFirstNameAndLastName(firstName, lastName);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons);
        List<PersonInfo> personsInfo = personInfoService.getPersonInfoList(persons, medicalRecords);
        serialization.personInfoSerialization(personsInfo, getCurrentMethodName(), firstName, lastName);
    }

    public void getAllResidentsEmails(String city) {
        List<Person> persons = personService.getPersonsByCity(city);
        serialization.communityEmailSerialization(persons, getCurrentMethodName(), city);
    }

    public boolean isMedicalRecordIsAtPerson(MedicalRecord medicalRecord, Person person) {
        if (person.getFirstName().equals(medicalRecord.getFirstName())
                && person.getLastName().equals(medicalRecord.getLastName())) {
            return true;
        }
        return false;
    }

    public static String getCurrentMethodName() {
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
        return stackTraceElement.getMethodName();
    }

    public static String getArgumentsAsString(Object... args) {
        return Arrays.toString(args);
    }
}
