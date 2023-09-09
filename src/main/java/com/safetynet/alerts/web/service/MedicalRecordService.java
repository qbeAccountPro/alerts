package com.safetynet.alerts.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.communUtilts.DataManipulationUtils;
import com.safetynet.alerts.web.dao.MedicalRecordDao;
import com.safetynet.alerts.web.deserialization.model.MedicalRecordDeserialization;
import com.safetynet.alerts.web.logging.EndpointsLogger;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

/**
 * Some javadoc.
 * 
 * This service class provides operations related to the MedicalRecord entity.
 * It interacts with the MedicalRecordDao to perform CRUD operations on
 * MedicalRecord objects.
 */
@Service
public class MedicalRecordService {
  private DataManipulationUtils beanService = new DataManipulationUtils();
  private final MedicalRecordDao medicalRecordDao;
  private final PersonService personService;
  private EndpointsLogger log = new EndpointsLogger();

  @Autowired
  public MedicalRecordService(
      MedicalRecordDao medicalrecordDao, PersonService personService) {
    this.medicalRecordDao = medicalrecordDao;
    this.personService = personService;
  }

  /**
   * Some javadoc.
   * 
   * Saves a new or update a medicalRecord object.
   *
   * @param medicalRecord The MedicalRecord object to save.
   */
  public void saveMedicalRecord(MedicalRecord medicalRecord) {
    medicalRecordDao.save(medicalRecord);
  }

  /**
   * Some javadoc.
   * 
   * Get a list of medical records corresponding to children medicalRecord objects
   * from a list of
   * resident medical records.
   *
   * @param residentMedicalRecords The list of resident medical records for which
   *                               to retrieve children medical records.
   * @return A list of MedicalRecord objects corresponding to children from the
   *         provided resident medical records.
   */
  public List<MedicalRecord> getChildrenMedicalRecords(List<MedicalRecord> residentMedicalRecords) {
    List<MedicalRecord> childrenMedicalRecords = new ArrayList<>();
    for (MedicalRecord residentMedicalRecord : residentMedicalRecords) {
      if (isMinor(residentMedicalRecord.getBirthdate())) {
        childrenMedicalRecords.add(residentMedicalRecord);
      }
    }
    return childrenMedicalRecords;
  }

  /**
   * Some javadoc.
   * 
   * Checks if a person with the given birthdate is considered as a minor (under
   * 18
   * years old).
   *
   * @param birthdate The birthdate of the person in the format "MM/dd/yyyy".
   * @return true if the person is a minor, false otherwise.
   */
  public boolean isMinor(String birthdate) {
    if (beanService.convertBirthdateToAge(birthdate) <= 18) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * Some javadoc.
   * 
   * Calculates the number of minors (individuals under 18 years old) based on
   * their birthdates.
   *
   * @param medicalRecords A list of medical records containing birthdates.
   * @return The number of minors in the provided list of medical records.
   */
  public int getMinorsNumber(List<MedicalRecord> medicalRecords) {
    int minors = 0;
    for (MedicalRecord medicalRecord : medicalRecords) {
      if (isMinor(medicalRecord.getBirthdate())) {
        minors++;
      }
    }
    return minors;
  }

  /**
   * Some javadoc.
   * 
   * Calculates the number of adults (individuals 18 years old or older) based on
   * their birthdates.
   *
   * @param medicalRecords A list of medical records containing birthdates.
   * @return The number of adults in the provided list of medical records.
   */
  public int getAdultsNumber(List<MedicalRecord> medicalRecords) {
    int adults = 0;
    for (MedicalRecord medicalRecord : medicalRecords) {
      if (!isMinor(medicalRecord.getBirthdate())) {
        adults++;
      }
    }
    return adults;
  }

  /**
   * Some javadoc.
   * 
   * Retrieves a list of medical records for adults (individuals 18 years old or
   * older) based on their birthdates.
   *
   * @param medicalRecords A list of medical records containing birthdates.
   * @return A list of medical records for adults in the provided list of medical
   *         records.
   */
  public List<MedicalRecord> getAdultsMedicalRecords(List<MedicalRecord> medicalRecords) {
    List<MedicalRecord> adultsMedicalRecords = new ArrayList<>();
    for (MedicalRecord medicalRecord : medicalRecords) {
      if (!isMinor(medicalRecord.getBirthdate())) {
        adultsMedicalRecords.add(medicalRecord);
      }
    }
    return adultsMedicalRecords;
  }

  /**
   * Some javadoc.
   * 
   * Saves a new or update a medicalRecord object.
   *
   * @param medicalrecordDeserialization The MedicalRecord object to save in the
   *                                     deserialization format.
   * @param personId                     The id from the person corresponding.
   */
  public void saveMedicalRecord(MedicalRecordDeserialization medicalrecordDeserialization, int personId) {
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setIdPerson(personId);
    medicalRecord.setBirthdate(medicalrecordDeserialization.getBirthdate());
    medicalRecord.setAllergies(medicalrecordDeserialization.getAllergies());
    medicalRecord.setMedications(medicalrecordDeserialization.getMedications());
    medicalRecordDao.save(medicalRecord);
  }

  /**
   * Some javadoc.
   * 
   * Adds a new medical record for a person or returns an error if the person does
   * not exist or already has a medical record.
   *
   * @param medicalrecordDeserialize The deserialized medical record data.
   * @param methodeName              The name of the method invoking the
   *                                 operation.
   * @return A ResponseEntity indicating the result of the operation.
   */
  public ResponseEntity<String> addMedicalRecord(MedicalRecordDeserialization medicalrecordDeserialize,
      String methodeName) {
    // Get the person corresponding :
    String firstName = medicalrecordDeserialize.getFirstName();
    String lastName = medicalrecordDeserialize.getLastName();

    Person person = personService.getPersonByFirstAndLastName(firstName, lastName);
    if (person == null) {
      return log.argumentHasNoMatch(methodeName);
    } else {
      MedicalRecord medicalRecord = medicalRecordDao.findByIdPerson(person.getId());
      if (medicalRecord == null) {
        saveMedicalRecord(medicalrecordDeserialize, person.getId());
        return log.addedSuccessfully(methodeName);
      } else {
        return log.ExistingMedicalRecord(methodeName);
      }
    }
  }

  /**
   * Some javadoc.
   * 
   * Updates the medical record of a person based on their first name and last
   * name.
   *
   * @param firstName                The first name of the person.
   * @param lastName                 The last name of the person.
   * @param medicalrecordDeserialize The deserialized medical record data.
   * @param methodeName              The name of the method invoking the
   *                                 operation.
   * @return A ResponseEntity indicating the result of the operation.
   */
  public ResponseEntity<String> updateMedicalRecord(String firstName, String lastName,
      MedicalRecordDeserialization medicalrecordDeserialize, String methodeName) {
    // Get the matching persons by first and last Name :
    Person person = personService.getPersonByFirstAndLastName(firstName, lastName);
    if (person == null) {
      return log.argumentHasNoMatch(methodeName);
    } else {
      MedicalRecord medicalRecord = medicalRecordDao.findByIdPerson(person.getId());
      if (medicalRecord == null) {
        return log.argumentHasNoMatch(methodeName);
      } else {
        medicalRecord.setBirthdate(medicalrecordDeserialize.getBirthdate());
        medicalRecord.setAllergies(medicalrecordDeserialize.getAllergies());
        medicalRecord.setMedications(medicalrecordDeserialize.getMedications());
        medicalRecordDao.save(medicalRecord);
        return log.updatedSuccessfully(methodeName);
      }
    }
  }

  /**
   * Some javadoc.
   * 
   * Deletes the medical record of a person based on their first name and last
   * name.
   *
   * @param firstName   The first name of the person.
   * @param lastName    The last name of the person.
   * @param methodeName The name of the method invoking the operation.
   * @return A ResponseEntity indicating the result of the operation.
   */
  public ResponseEntity<String> deleteMedicalRecord(String firstName, String lastName, String methodeName) {
    // Get the person correpondig at this first and last name :
    Person person = personService.getPersonByFirstAndLastName(firstName, lastName);
    if (person == null) {
      return log.argumentHasNoMatch(methodeName);
    }
    // Get the corresponding medicalRecord from the person
    MedicalRecord medicalRecord = medicalRecordDao.findByIdPerson(person.getId());
    if (medicalRecord == null) {
      return log.argumentHasNoMatch(methodeName);
    } else {
      medicalRecordDao.deleteById(medicalRecord.getId());
      return log.deletedSuccessfully(methodeName);
    }
  }

  /**
   * Some javadoc.
   * 
   * Retrieves a list of medical records for a list of persons.
   *
   * @param persons The list of persons for which to retrieve medical records.
   * @return A list of medical records associated with the provided list of
   *         persons.
   */
  public List<MedicalRecord> getMedicalRecordsByPersons(List<Person> persons) {
    List<MedicalRecord> medicalRecords = new ArrayList<>();
    for (Person person : persons) {
      medicalRecords.add(medicalRecordDao.findByIdPerson(person.getId()));
    }
    return medicalRecords;
  }

  /**
   * Some javadoc.
   * 
   * Retrieves the medical record for a specific person.
   *
   * @param person The person for which to retrieve the medical record.
   * @return The medical record associated with the provided person, or null if
   *         not found.
   */
  public MedicalRecord getMedicalRecordByPerson(Person person) {
    return medicalRecordDao.findByIdPerson(person.getId());
  }

  /**
   * Retrieves a list of all medical records in the system.
   *
   * @return A list of all medical records.
   */
  public List<MedicalRecord> getAllMedicalRecords() {
    return medicalRecordDao.findAll();
  }
}