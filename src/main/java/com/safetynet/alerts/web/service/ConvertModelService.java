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

/**
 * Some javadoc.
 * 
 * This class provides methods to convert data from deserialization models to
 * domain models.
 * It is responsible for creating objects such as households, firestations,
 * medical records, and persons based on the data provided in deserialization
 * models.
 */
public class ConvertModelService {

  /**
   * Some javadoc.
   * 
   * Retrieves a list of households based on person and firestation
   * deserialization data.
   *
   * @param personsDeserialization      The list of person deserialization data.
   * @param firestationDeserializations The list of firestation deserialization
   *                                    data.
   * @return A list of households created from the provided data.
   */
  public List<Household> getHouseholds(List<PersonDeserialization> personsDeserialization,
      List<FirestationDeserialization> firestationDeserializations) {
    int id = 1;
    List<Household> households = new ArrayList<>();
    List<String> addresses = new ArrayList<>();
    // Add addresses from Firestation
    for (FirestationDeserialization firestationDeserialization : firestationDeserializations) {
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
    for (String address : addresses) {
      Household newHousehold = new Household(id, address);
      households.add(newHousehold);
      id++;
    }
    return households;
  }

  /**
   * Some javadoc.
   * 
   * Retrieves a list of firestations based on firestation deserialization data.
   *
   * @param firestationDeserializations The list of firestation deserialization
   *                                    data.
   * @param households                  The list of households.
   * @return A list of firestations created from the provided data.
   */
  public List<Firestation> getFirestations(List<FirestationDeserialization> firestationDeserializations,
      List<Household> households) {
    int id = 1;
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
        newFirestation.setId(id);
        firestations.add(newFirestation);
        id++;
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
              if(!idHouseholds.contains(household.getId())){
              idHouseholds.add(household.getId());
              }
              break;
            }
          }
        }
      }
      firestation.setIdHouseholds(idHouseholds);
    }
    return firestations;
  }

  /**
   * Some javadoc.
   * 
   * Retrieves a list of medical records based on medical record deserialization
   * data and a list of persons.
   *
   * @param medicalRecordDeserializations The list of medical record
   *                                      deserialization data.
   * @param persons                       The list of persons.
   * @return A list of medical records created from the provided data.
   */
  public List<MedicalRecord> getMedicalRecords(List<MedicalRecordDeserialization> medicalRecordDeserializations,
      List<Person> persons) {
    List<MedicalRecord> medicalRecords = new ArrayList<>();
    int id = 1;

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
      medicalRecord.setId(id);
      medicalRecord.setBirthdate(medicalRecordDeserialization.getBirthdate());
      medicalRecord.setMedications(medicalRecordDeserialization.getMedications());
      medicalRecord.setAllergies(medicalRecordDeserialization.getAllergies());
      medicalRecords.add(medicalRecord);
      id++;
    }
    return medicalRecords;
  }

  /**
   * Some javadoc.
   * 
   * Retrieves a list of persons based on person deserialization data and a list
   * of households.
   *
   * @param personDeserializations The list of person deserialization data.
   * @param households             The list of households.
   * @return A list of persons created from the provided data.
   */
  public List<Person> getPersons(List<PersonDeserialization> personDeserializations, List<Household> households) {
    List<Person> persons = new ArrayList<>();
    int id = 1;
    for (PersonDeserialization personDeserialization : personDeserializations) {
      Person person = new Person();
      for (Household household : households) {
        if (personDeserialization.getAddress().equals(household.getAddress())) {
          person.setIdHousehold(household.getId());
          break;
        }
      }
      person.setId(id);
      person.setFirstName(personDeserialization.getFirstName());
      person.setLastName(personDeserialization.getLastName());
      person.setCity(personDeserialization.getCity());
      person.setEmail(personDeserialization.getEmail());
      person.setPhone(personDeserialization.getPhone());
      person.setEmail(personDeserialization.getEmail());
      person.setZip(personDeserialization.getZip());
      persons.add(person);
      id++;
    }
    return persons;
  }
}