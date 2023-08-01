package com.safetynet.alerts.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.springframework.stereotype.Service;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.service.SerializationService;

@Service
public class URLSService {

    private final FirestationService firestationService;
    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;
    private final SerializationService serializationService;
    private BeanService beanService = new BeanService();

    public URLSService(FirestationService firestationService, PersonService personService,
            MedicalRecordService medicalRecordService, SerializationService serializationService) {
        this.firestationService = firestationService;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
        this.serializationService = serializationService;

    }

    public void getFireStationData(String station) {
        List<String> addresses = firestationService.getAddressesCoveredByStationfireNumber(station);
        List<Person> residents = personService.getPersonsListByAddressesList(addresses);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(residents);
        List<Integer> minorsThenAdultsNumbers = medicalRecordService.getMinorsAndAdultsNumbers(medicalRecords);
        int minorsNumber = minorsThenAdultsNumbers.get(0);
        int adultsNumber = minorsThenAdultsNumbers.get(1);

        serializationService.firestationService(residents, getCurrentMethodName(),
                getArgumentsAsString(station), minorsNumber, adultsNumber);
    }

    public void getChildrenByAddress(String address) {
        List<Person> persons = personService.getPersonsListByAddress(address);
        List<MedicalRecord> residentsMedicalRecord = medicalRecordService.getMedicalRecordsByPersons(persons);
        List<MedicalRecord> residentChildrenMedicalRecords = medicalRecordService
                .getMedicalRecordsOnlyFromChild(residentsMedicalRecord);
        if (residentChildrenMedicalRecords != null) {
            List<Person> residentsOtherThanChildren = new ArrayList<>();
            for (Person person : persons) {
                for (MedicalRecord residentChildMedicalRecord : residentChildrenMedicalRecords) {
                    if (person.getFirstName().equals(residentChildMedicalRecord.getFirstName())
                            && person.getLastName().equals(residentChildMedicalRecord.getLastName())) {
                        break;
                    }
                    residentsOtherThanChildren.add(person);
                }
            }
            serializationService.serializationChildAlertQuery(residentChildrenMedicalRecords, persons,
                    getCurrentMethodName(), getArgumentsAsString(address));
            for (MedicalRecord residentChildMedicalRecord : residentChildrenMedicalRecords) {
                System.out.println("FirstName : " + residentChildMedicalRecord.getFirstName() + ", LastName : "
                        + residentChildMedicalRecord.getLastName() + ", Age : "
                        + beanService.convertBirthdateToAge(residentChildMedicalRecord.getBirthdate()));
            }
            System.out.println("Other residents : ");
            for (Person residentOtherThanChildren : residentsOtherThanChildren) {
                System.out.println("FirstName : " + residentOtherThanChildren.getFirstName() + ", LastName : "
                        + residentOtherThanChildren.getLastName());
            }
        } else {
            System.out.println("No children at this address");
        }

    }

    public void getPhoneNumbersByFirestationNumber(String station) {
        List<String> addresses = firestationService.getAddressesCoveredByStationfireNumber(station);
        List<Person> personsCoveredByFirestation = personService.getPersonsListByAddressesList(addresses);
        if (personsCoveredByFirestation != null) {
            for (Person person : personsCoveredByFirestation) {
                System.out.println("Tel :" + person.getPhone());
            }
        } else {
            System.out.println("Nobody at this station");
        }
    }

    public void getStationAndPersonsByAddress(String address) {
        List<Person> persons = personService.getPersonsListByAddress(address);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons);
        String firestationNumber = firestationService.getStationNumberByAdress(address);

        
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                if (person.getFirstName().equals(medicalRecord.getFirstName())
                        && person.getLastName().equals(medicalRecord.getLastName())) {
                    System.out.println("FirstName : " + person.getFirstName() + ", LastName : " + person.getLastName()
                            + ", Tel : " + person.getPhone() + ", Age : "
                            + beanService.convertBirthdateToAge(medicalRecord.getBirthdate())
                            + ", Medical recods : " + medicalRecord.getAllergies() + medicalRecord.getMedications());
                    break;
                }
            }
        }

    }

    public void getPersonsWithTheirMedicalRecordsByStationNumber(String station) {
        List<String> addresses = firestationService.getAddressesCoveredByStationfireNumber(station);
        List<Person> persons = personService.getPersonsListByAddressesList(addresses);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons);
        for (String address : addresses) {
            System.out.println("From this address '" + address + "' we got this persons :");
            for (Person person : persons) {
                if (person.getAddress().equals(address)) {
                    for (MedicalRecord medicalRecord : medicalRecords) {
                        if (isMedicalRecordIsAtPerson(medicalRecord, person)) {
                            System.out.println(
                                    "FirstName : " + person.getFirstName() + ", LastName : " + person.getLastName()
                                            + ", Tel : " + person.getPhone() + ", Age : "
                                            + beanService.convertBirthdateToAge(medicalRecord.getBirthdate())
                                            + ", Medical recods : " + medicalRecord.getAllergies()
                                            + medicalRecord.getMedications());
                            break;
                        }
                    }
                }
            }

        }
    }

    public void getPersonInfoByFirstNameAndLastName(String firstName, String lastName) {
        List<Person> persons = personService.getPersonsByFirstNameAndLastName(firstName, lastName);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(persons);
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                if (isMedicalRecordIsAtPerson(medicalRecord, person)) {
                    System.out.println(
                            "LastName : " + person.getLastName()
                                    + ", Addres : " + person.getAddress() + ", Age : "
                                    + beanService.convertBirthdateToAge(medicalRecord.getBirthdate())
                                    + ", Mail :" + person.getEmail()
                                    + ", Medical recods : " + medicalRecord.getAllergies()
                                    + medicalRecord.getMedications());
                    break;
                }

            }
        }
    }

    public void getAllResidentsEmails(String city) {
        List<Person> persons = personService.getAllPersons();
        for (Person person : persons) {
            System.out.println(person.getEmail());
        }
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
