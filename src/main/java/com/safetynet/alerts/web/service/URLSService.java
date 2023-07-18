package com.safetynet.alerts.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

@Service
public class URLSService {

    private final FirestationService firestationService;
    private final PersonService personService;
    private final MedicalRecordService medicalRecordService;

    public URLSService(FirestationService firestationService, PersonService personService,
            MedicalRecordService medicalRecordService) {
        this.firestationService = firestationService;
        this.personService = personService;
        this.medicalRecordService = medicalRecordService;
    }

    public void getFireStationData(String station) {
        List<String> addresses = firestationService.getAddressesCoveredByStationfireNumber(station);
        List<Person> residents = personService.getPersonsListByAddressesList(addresses);
        List<MedicalRecord> medicalRecords = medicalRecordService.getMedicalRecordsByPersons(residents);
        System.out.println("Voici la liste des habitants :");
        for (Person resident : residents) {
            System.out.println("FirstName : " + resident.getFirstName() + ", LastName : " + resident.getLastName()
                    + ", Address : " + resident.getAddress() + ", Tel : " + resident.getPhone());
        }
        System.out.println(medicalRecordService.getMinorsAndAdultsNumbers(medicalRecords));
    }

    public void getChildrenByAddress(String address) {
        List<Person> residents = personService.getPersonsListByAddress(address);
        List<MedicalRecord> residentsMedicalRecord = medicalRecordService.getMedicalRecordsByPersons(residents);
        List<MedicalRecord> residentChildrenMedicalRecords = medicalRecordService
                .getMedicalRecordsOnlyFromChild(residentsMedicalRecord);
        if (residentChildrenMedicalRecords != null) {
            List<Person> residentsOtherThanChildren = new ArrayList<>();
            for (Person resident : residents) {
                for (MedicalRecord residentChildMedicalRecord : residentChildrenMedicalRecords) {
                    if (resident.getFirstName().equals(residentChildMedicalRecord.getFirstName())
                            && resident.getLastName().equals(residentChildMedicalRecord.getLastName())) {
                        break;
                    }
                    residentsOtherThanChildren.add(resident);
                }
            }
            for (MedicalRecord residentChildMedicalRecord : residentChildrenMedicalRecords) {
                System.out.println("FirstName : " + residentChildMedicalRecord.getFirstName() + ", LastName : "
                        + residentChildMedicalRecord.getLastName() + ", Age : "
                        + medicalRecordService.convertBirthdateToAge(residentChildMedicalRecord.getBirthdate()));
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
}
