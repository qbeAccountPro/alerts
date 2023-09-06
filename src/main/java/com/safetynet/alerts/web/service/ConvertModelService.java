package com.safetynet.alerts.web.service;

import java.util.ArrayList;
import java.util.List;

import com.safetynet.alerts.web.deserialization.model.FirestationDeserialization;
import com.safetynet.alerts.web.deserialization.model.MedicalRecordDeserialization;
import com.safetynet.alerts.web.deserialization.model.PersonDeserialization;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

public class ConvertModelService {

    public List<Household> getHouseholds(List<PersonDeserialization> personsDeserialization,
            List<FirestationDeserialization> firestationDeserializations, List<Firestation> firestations) {
        List<Household> households = new ArrayList<>();
        households = setAddressesOfHouseholds(firestationDeserializations,
                personsDeserialization);
        return households;
    }

    private List<Household> setAddressesOfHouseholds(
            List<FirestationDeserialization> firestationsDeserialization,
            List<PersonDeserialization> personsDeserialization) {
        List<String> addresses = new ArrayList<>();

        // Add addresses from Firestation
        for (FirestationDeserialization firestationDeserialization : firestationsDeserialization) {
            if (addresses.isEmpty()) {
                addresses.add(firestationDeserialization.getAddress());
            } else {
                Boolean ifAddressMatch = false;
                for (String address : addresses) {
                    ifAddressMatch = firestationDeserialization.getAddress().equals(address);
                    if (ifAddressMatch) {
                        break;
                    }
                }
                if (!ifAddressMatch) {
                    addresses.add(firestationDeserialization.getAddress());
                }
            }
        }

        // Add addresses from Person if this address is not covered by any firestation
        for (PersonDeserialization personDeserialization : personsDeserialization) {
            if (addresses.isEmpty()) {
                addresses.add(personDeserialization.getAddress());
            } else {
                Boolean ifAddressMatch = false;
                for (String address : addresses) {
                    ifAddressMatch = personDeserialization.getAddress().equals(address);
                    if (ifAddressMatch) {
                        break;
                    }
                }
                if (!ifAddressMatch) {
                    addresses.add(personDeserialization.getAddress());
                }
            }
        }

        List<Household> households = new ArrayList<>();
        for (String address : addresses) {
            Household newHousehold = new Household(0, address);
            households.add(newHousehold);
        }

        return households;
    }

    public List<Firestation> getFirestations(List<FirestationDeserialization> firestationDeserializations,
            List<Household> households) {
        List<Firestation> firestations = new ArrayList<>();

        // Get a list of firestation with only STATION.
        for (FirestationDeserialization firestationDeserialization : firestationDeserializations) {
            Firestation newFirestation = new Firestation();
            newFirestation.setStation(firestationDeserialization.getStation());

            Boolean ifStationExists = false;
            for (Firestation firestation : firestations) {
                if (firestation.getStation().equals(newFirestation.getStation())) {
                    ifStationExists = true;
                    break;
                }
            }
            if (!ifStationExists) {
                firestations.add(newFirestation);
            }
        }

        // Set from the precedent list the idHouseholds (Corresponding at the
        // addresses).
        for (Firestation firestation : firestations) {
            ArrayList<Integer> idHouseholds = new ArrayList<>();
            for (FirestationDeserialization firestationDeserialization : firestationDeserializations) {
                if (firestationDeserialization.getStation().equals(firestation.getStation())) {
                    for (Household household : households) {
                        if (household.getAddress().equals(firestationDeserialization.getAddress())) {
                            idHouseholds.add(household.getId());
                            break;
                        }
                    }
                }
            }
            firestation.setIdHouseholds(idHouseholds);
        }
        return firestations;
    }

    public List<MedicalRecord> getMedicalRecords(List<MedicalRecordDeserialization> medicalRecordDeserializations,
            List<Person> persons) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();

        for (MedicalRecordDeserialization medicalRecordDeserialization : medicalRecordDeserializations) {
            MedicalRecord medicalRecord = new MedicalRecord();
            for (Person person : persons) {
                Boolean checkFirstName = person.getFirstName().equals(medicalRecordDeserialization.getFirstName());
                Boolean checkLastName = person.getLastName().equals(medicalRecordDeserialization.getLastName());
                if (checkFirstName && checkLastName) {
                    medicalRecord.setIdPerson(person.getId());
                    break;
                }
            }
            medicalRecord.setBirthdate(medicalRecordDeserialization.getBirthdate());
            medicalRecord.setMedications(medicalRecordDeserialization.getMedications());
            medicalRecord.setAllergies(medicalRecordDeserialization.getAllergies());
            medicalRecords.add(medicalRecord);
        }

        return medicalRecords;
    }

    public List<Person> getPersons(List<PersonDeserialization> personDeserializations, List<Household> households) {
        List<Person> persons = new ArrayList<>();
        for (PersonDeserialization personDeserialization : personDeserializations) {
            Person person = new Person();
            for (Household household : households) {
                if (personDeserialization.getAddress().equals(household.getAddress())) {
                    person.setIdHousehold(household.getId());
                    break;
                }
            }
            person.setFirstName(personDeserialization.getFirstName());
            person.setLastName(personDeserialization.getLastName());
            person.setCity(personDeserialization.getCity());
            person.setEmail(personDeserialization.getEmail());
            person.setPhone(personDeserialization.getPhone());
            person.setEmail(personDeserialization.getEmail());
            person.setZip(personDeserialization.getZip());
            persons.add(person);
        }
        return persons;
    }
}
